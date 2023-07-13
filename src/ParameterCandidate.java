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

    ParameterCandidate(double DoubledOrMissingPawnValue, double AvailableMoveValue,double RookCoveredValue, double BishopCoveredValue, double KnightCoveredValue,
                       double QueenCoveredValue, double PawnCoveredValue, double PawnValue, double KnightValue, double BishopValue, double RookValue, double QueenValue) {
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
    }
}