package UI;

public class ProgramLog {
    private static final int MAX_LINE = 12;

    private String[] text = new String[MAX_LINE];
    private int line;
    public ProgramLog(){
        line = 0;
    }
    public void addLog(String log){
        if(line == MAX_LINE){
            String[] temp = text;
            for(int i = 1; i < MAX_LINE;i++) {
                text[i-1] = temp[i];
            }
            text[MAX_LINE-1] = log;
            return;
        }
        text[line] = log;
        line++;
    }
    public void addLogSameLine(String log){
        if(line == 0)
            text[line] = log;
        else
            text[line-1] = text[line-1] + log;
    }
    public String getText(int i){
        return text[i];
    }
    public int getLineCount(){
        return line;
    }
}
