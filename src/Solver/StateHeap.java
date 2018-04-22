package Solver;

public class StateHeap {
    private State[] stateHeap;
    private int size;
    private int maxSize;
    private static final int FRONT = 1;

    public StateHeap(int maxSize){
        this.maxSize = maxSize;
        this.size = 0;
        stateHeap = new State[this.maxSize + 1];
        stateHeap[0] = new State(Integer.MAX_VALUE);
    }
    private int parent(int pos){
        return pos/2;
    }
    private int leftChild(int pos){
        return 2*pos;
    }
    private int rightChild(int pos){
        return (2*pos)+1;
    }
    private boolean isLeaf(int pos){
        return pos >= (size / 2) && pos <= size;
    }
    private void swap(int fpos,int spos){
        State temp = stateHeap[fpos];
        stateHeap[fpos] = stateHeap[spos];
        stateHeap[spos] = temp;
    }
    private void maxHeapify(int pos){
        if(!isLeaf(pos)){
            if(stateHeap[pos].getScore() < stateHeap[leftChild(pos)].getScore() || stateHeap[pos].getScore() < stateHeap[rightChild(pos)].getScore()){
                if(stateHeap[leftChild(pos)].getScore() > stateHeap[rightChild(pos)].getScore()){
                    swap(pos,leftChild(pos));
                    maxHeapify(leftChild(pos));
                }
                else{
                    swap(pos,rightChild(pos));
                    maxHeapify(rightChild(pos));
                }
            }
        }
    }
    public void insert(State input){
        stateHeap[++size] = input;
        int current = size;
        while(stateHeap[current].getScore() > stateHeap[parent(current)].getScore()){
            swap(current,parent(current));
            current = parent(current);
        }
    }
    public void maxHeap(){
        for(int pos = (size/2);pos>=1;pos--)
            maxHeapify(pos);
    }
    public State remove(){
        State popped = stateHeap[FRONT];
        stateHeap[FRONT] = stateHeap[size--];
        maxHeapify(FRONT);
        return popped;
    }
}
