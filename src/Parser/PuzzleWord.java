package Parser;

public class PuzzleWord {
    private int direction; // 0 ACROSS //1 DOWN
    private int questionNo;
    private int colNo;
    private int rowNo;
    private int length;
    private String hint;
    private String buffer;
    private int start;
    private int end;
    private int size;

    public PuzzleWord(int direction ,int colNo, int rowNo, int size, int questionNo, String hint){
        this.direction = direction;
        this.start = colNo;
        this.end = rowNo;
        this.size = size;
        this.questionNo = questionNo;
        this.hint = hint;
    }

    public PuzzleWord( int colNo, int rowNo, int length, String hint, int loc, int questionNo){
        this.colNo = colNo;
        this.rowNo = rowNo;
        this.length = length;
        this.hint = hint;
        this.direction = loc;
        this.questionNo = questionNo;
        buffer = "";
        for (int i = 0; i < buffer.length(); i++)
            buffer += '?';
    }

    // Black squares corresponds to *
    // Unknown corresponds to ?
    /*
    public PuzzleWord( int colNo, int rowNo, int length, String hint, int start, int end){
        this.colNo = colNo;
        this.rowNo = rowNo;
        this.length = length;
        this.hint = hint;
        this.start = start;
        this.end = end;
        buffer = "";
        for (int i = 0; i < buffer.length(); i++) {
            if ( i < start || i > end){
                buffer += '*';
                continue;
            }
            buffer += '?';
        }
    }
    */
    public int getColNo() {
        return colNo;
    }

    public void setColNo(int colNo) {
        this.colNo = colNo;
    }

    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getBuffer() {
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }



    public void updateCharAt( int index, char c){
        String tmp = "";
        for (int i = 0; i < getLength(); i++){
            if ( i == index) {
                tmp += c;
                continue;
            }
            tmp += buffer.charAt(i);
        }
        buffer = tmp;
    }

    public int getDirection() {
        return direction;
    }

    public int getQuestionNo() {
        return questionNo;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getSize() {
        return size;
    }
}
