import java.util.concurrent.*;

public class MCTS {
    private static final int SIMULATION_COUNT = 10000;
    private MCTS_Node _root;

    public ChessMove findBestMove(Chessboard initialState, boolean whiteTurn) {
        MCTS_Node root = new MCTS_Node(initialState, whiteTurn, new ChessMove(0,0));
        //root.state.printBoard();
        // Set the initial game state as the root node

        for (int i = 0; i < SIMULATION_COUNT; i++) {
            MCTS_Node selectedNode = select(root);
            //System.out.println("root visits:" + root.visits + " root score:" + root.score);
            //System.out.println("select:" + selectedNode.move);
            MCTS_Node expandedNode = expand(selectedNode);
            //System.out.println("expand:" + expandedNode.move);
            double score = simulate(expandedNode);
            backpropagate(expandedNode, score);
        }

        // Choose the best move based on the statistics
        //System.out.println(root.children.size());
        //root.printTree();
        return getBestMove(root);
    }

    public ChessMove findBestMove_AB(Chessboard initialState, boolean whiteTurn) {
        MCTS_Node root = new MCTS_Node(initialState, whiteTurn, new ChessMove(0,0));
        AI_Board ai = new AI_Board(initialState);
        //root.state.printBoard();
        // Set the initial game state as the root node

        for (int i = 0; i < SIMULATION_COUNT; i++) {
            MCTS_Node selectedNode = select(root);
            //System.out.println("root visits:" + root.visits + " root score:" + root.score);
            //System.out.println("select:" + selectedNode.move);
            MCTS_Node expandedNode = expand(selectedNode);
            //System.out.println("expand:" + expandedNode.move);
            double score = simulate_AB(expandedNode, ai);
            backpropagate(expandedNode, score);
        }

        // Choose the best move based on the statistics
        System.out.println("root children: " + root.children.size());
        //root.printTree();
        return getBestMove(root);
    }

    //current player and duration in ms
    public ChessMove iterativeDeepening_MCTS(Chessboard initialState, boolean whiteTurn, long DURATION) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + DURATION;
        _root = new MCTS_Node(initialState, whiteTurn, new ChessMove(0,0));
        int i = 0;

        while (System.currentTimeMillis() < endTime) {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<Void> future = executor.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    //return randomMove(board,!board.lastPlayer);
                    MCTS_Node selectedNode = select(_root);
                    //System.out.println("root visits:" + root.visits + " root score:" + root.score);
                    //System.out.println("select:" + selectedNode.move);
                    MCTS_Node expandedNode = expand(selectedNode);
                    //System.out.println("expand:" + expandedNode.move);
                    double score = simulate(expandedNode);
                    backpropagate(expandedNode, score);
                    return null;
                }
            });
            try {
                //wait for result but with only the time that is left as a limit: endtime - current time
                future.get(endTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
            } catch (TimeoutException f) {
                future.cancel(true);
                //throw new RuntimeException("Timeout", f);
            } catch (ExecutionException | InterruptedException ex) {
                throw new RuntimeException(ex);
            } finally {
                executor.shutdownNow();
            }
        }

        return getBestMove(_root);
    }

    /*
    CHAT GPT BS
    private MCTS_Node select(MCTS_Node node) {
        double C = Math.sqrt(2); // Exploration constant

        while (node.children.size()>0) {
            MCTS_Node bestChild = null;
            double bestUCT = Double.NEGATIVE_INFINITY;

            for (MCTS_Node child : node.children) {
                double UCT = child.score / child.visits + C * Math.sqrt(Math.log(node.visits) / child.visits);

                if (UCT > bestUCT) {
                    bestChild = child;
                    bestUCT = UCT;
                }
            }

            node = bestChild;
        }

        return node;
    }*/


    private MCTS_Node select(MCTS_Node node) {
        // Perform tree traversal to select a node for expansion
        // Use a selection strategy like UCT to balance exploration and exploitation
        // Consider node's visits and scores to determine the most promising child
        double bestUCT = -1.0;
        MCTS_Node bestNode = new MCTS_Node();
        //MCTS_Node bestNode = node;

        double C = Math.sqrt(2);
        //double C = 2.0;

        if(node.children.size() == 0){
            //System.out.println("Children are null");
            return node;
        }else{
            for (MCTS_Node child: node.children) {
                //double UCT = (child.score/child.visits) + C * Math.sqrt(Math.log(child.parent.visits)/child.visits);
                double UCT;
                if(child.visits == 0){
                    UCT = 0.5;
                }else {
                    //UCT = (child.score/child.visits);
                    UCT = (child.score/child.visits) + C * Math.sqrt(Math.log(node.visits)/child.visits);
                    //UCT = (child.score/child.visits) + C * child.parent.visits/child.visits;
                }
                if(UCT>bestUCT){
                    bestNode = child;
                    bestUCT = UCT;
                }
            }
        }
        MCTS_Node selectedNode = this.select(bestNode);

        int parent_visits;
        if(selectedNode.parent == null){
            parent_visits = 1;
        }else{
            parent_visits = selectedNode.parent.visits;
        }

        double UCT;
        if(selectedNode.visits == 0){
            UCT = 0.5;
        }else {
            //UCT = (selectedNode.score/selectedNode.visits);
            UCT = (selectedNode.score/selectedNode.visits) + C * Math.sqrt(Math.log(parent_visits)/selectedNode.visits);
            //UCT = (selectedNode.score/selectedNode.visits) + C * parent_visits/selectedNode.visits;
        }

        int node_parent_visits;
        if(node.parent == null){
            node_parent_visits = 1;
        }else{
            node_parent_visits = node.parent.visits;
        }

        double node_UCT;
        if(node.visits == 0){
            node_UCT = 0.5;
        }else {
            //UCT = (selectedNode.score/selectedNode.visits);
            node_UCT = (node.score/node.visits) + C * Math.sqrt(Math.log(node_parent_visits)/node.visits);
            //UCT = (selectedNode.score/selectedNode.visits) + C * parent_visits/selectedNode.visits;
        }

        //if(bestUCT > (selectedNode.score/selectedNode.visits) + C * Math.sqrt(Math.log(parent_visits)/selectedNode.visits)) {
        double max_UCT = Math.max(bestUCT, UCT);
        max_UCT = Math.max(node_UCT, max_UCT);
        if(max_UCT == node_UCT){
            return node;
        }else if (max_UCT == bestUCT){
            return bestNode;
        }else {
            return selectedNode;
        }
        /*if(bestUCT >= UCT){
            return bestNode;
        }else {
            return selectedNode;
        }*/
    }

    private MCTS_Node expand(MCTS_Node node) {
        // Expand the selected node by adding a new child node
        // Create a new game state by applying a legal move to the parent node's game state
        // Add the new node to the parent's children list
        //node.state.printBoard();
        Chessboard childBoard = new Chessboard(node.state);
        ChessMove random_move = ChessHelper.randomMove(childBoard,node.whiteTurn);
        if(random_move == null){
            return node;
        }
        childBoard.makeMove(node.whiteTurn, random_move);
        MCTS_Node expandedNode = new MCTS_Node(childBoard, !node.whiteTurn, random_move);
        expandedNode.parent = node;
        node.children.add(expandedNode);
        return expandedNode;
    }

    private double simulate_AB(MCTS_Node node, AI_Board ai){
        Chessboard chessboard = new Chessboard(node.state);
        boolean whiteTurn = node.whiteTurn;
        ai.setBoard(chessboard);
        ReturnObject result = ai.MTDF_simple(3,whiteTurn,chessboard,0.0);
        for(ChessMove move:result.moves){
            chessboard.makeMove(whiteTurn, move);
        }

        while(!chessboard.isGameOver_simple()){
            ChessMove randomMove = ChessHelper.randomMove(chessboard,whiteTurn);
            if(randomMove == null){
                return 0.0;
            }
            chessboard.makeMove(whiteTurn, randomMove);
            whiteTurn = !whiteTurn;
        }

        double score;
        if(chessboard.isCheckmate(!whiteTurn)){
            score = 1.0;
        }else {
            score = 0.0;
        }
        return score;

        //return result.eval;
    }

    private double simulate(MCTS_Node node) {
        // Simulate a game play-out from the given node
        // Use a simple policy to randomly select moves until reaching a terminal state
        // Return the final outcome of the simulation (e.g., win, loss, or draw)
        Chessboard chessboard = new Chessboard(node.state);
        boolean whiteTurn = node.whiteTurn;
        while(!chessboard.isGameOver_simple()){
            ChessMove randomMove = ChessHelper.randomMove(chessboard,whiteTurn);
            if(randomMove == null){
                return 0.0;
            }
            chessboard.makeMove(whiteTurn, randomMove);
            whiteTurn = !whiteTurn;
        }

        double score;
        if(chessboard.isCheckmate(!whiteTurn)){
            score = 1.0;
        }else {
            score = 0.0;
        }
        return score;
    }

    private void backpropagate(MCTS_Node node, double score) {
        // Update the statistics of the nodes along the path from the given node to the root
        // Increment visits and update the average score based on the simulation result

        // Repeat until reaching the root node
        while (node != null) {
            node.visits++;
            node.score += score;
            node = node.parent;
        }
    }

    private ChessMove getBestMove(MCTS_Node root) {
        // Choose the best move based on the statistics accumulated during the search
        // Typically, select the move with the highest visit count as it represents the most promising move
        MCTS_Node bestNode = root.children.get(0);
        for (MCTS_Node child: root.children) {
            if(child.visits > bestNode.visits){
                bestNode = child;
            }
        }
        //System.out.println("Root visits:" + root.visits + " Root score:" + root.score);
        //System.out.println("best move visits:" + bestNode.visits + " score:" + bestNode.score);
        ChessMove bestMove = bestNode.move;
        return bestMove;
    }
}

