package app.models.board;

public enum SudokuStatus {

    HAS_EMPTY_VALUES(0),
    WRONG(-1),
    OK(1);

    private final int code;

    SudokuStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
