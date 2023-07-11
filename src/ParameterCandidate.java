public class ParameterCandidate {
    double DoubledOrMissingPawnValue;
    double AvailableMoveValue;
    double RookCoveredValue;
    double BishopCoveredValue;
    double KnightCoveredValue;
    double QueenCoveredValue;
    double PawnCoveredValue;

    ParameterCandidate(double DoubledOrMissingPawnValue, double AvailableMoveValue,double RookCoveredValue, double BishopCoveredValue, double KnightCoveredValue, double QueenCoveredValue, double PawnCoveredValue) {
        this.DoubledOrMissingPawnValue = DoubledOrMissingPawnValue;
        this.AvailableMoveValue = AvailableMoveValue;
        this.RookCoveredValue = RookCoveredValue;
        this.BishopCoveredValue = BishopCoveredValue;
        this.KnightCoveredValue = KnightCoveredValue;
        this.QueenCoveredValue = QueenCoveredValue;
        this.PawnCoveredValue = PawnCoveredValue;
    }
}