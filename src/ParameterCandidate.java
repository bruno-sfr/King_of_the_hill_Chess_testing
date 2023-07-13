public class ParameterCandidate {
    double DoubledOrMissingPawnValue;
    double AvailableMoveValue;
    double RookCoveredValue;
    double BishopCoveredValue;
    double KnightCoveredValue;
    double QueenCoveredValue;
    double PawnCoveredValue;
    double PawnValue;
    double KnightValue;
    double BishopValue;
    double RookValue;
    double QueenValue;
    double param1;
    double param2;

    ParameterCandidate(double DoubledOrMissingPawnValue, double AvailableMoveValue,double RookCoveredValue, double BishopCoveredValue, double KnightCoveredValue,
                       double QueenCoveredValue, double PawnCoveredValue, double PawnValue, double KnightValue, double BishopValue, double RookValue, double QueenValue,
                       double param1, double param2) {
        this.DoubledOrMissingPawnValue = DoubledOrMissingPawnValue;
        this.AvailableMoveValue = AvailableMoveValue;
        this.RookCoveredValue = RookCoveredValue;
        this.BishopCoveredValue = BishopCoveredValue;
        this.KnightCoveredValue = KnightCoveredValue;
        this.QueenCoveredValue = QueenCoveredValue;
        this.PawnCoveredValue = PawnCoveredValue;
        this.PawnValue = PawnValue;
        this.KnightValue = KnightValue;
        this.BishopValue = BishopValue;
        this.RookValue = RookValue;
        this.QueenValue = QueenValue;
        this.param1 = param1;
        this.param2 = param2;
    }
}