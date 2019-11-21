package common;


public class TestTree {
    int posX;
    int length;
    int levels;
    int id;
    double leafLength;
    int amountOfLeaves;
    float score;
    Float treeHeight;
    int ID;

    public TestTree(int pPosX, int pLength, int pLevels) {
        this.posX = pPosX;
        this.length = pLength;
        this.levels = pLevels;
        this.id = 0;
        this.amountOfLeaves = (int)Math.pow(2, this.levels);
        for(leafLength=pLength; --pLevels>0; leafLength*=ITestConstants.GROW_PERCENTAGE);
        this.treeHeight = calculateHeight(this.length, common.ITestConstants.GROW_PERCENTAGE, leafLength);
    }

    private float calculateHeight(double pTrunkSize, double pGrowthRate, double pLeafSize) {
        float height = 0;
        while (pTrunkSize >= pLeafSize) {
            height += pTrunkSize;
            pTrunkSize = pTrunkSize - (pTrunkSize * pGrowthRate);
        }
        return  height;
    }
    
    public int getPosX() {
        return posX;
    }

    public int getLength() {
        return length;
    }

    public int getLevels() {
        return levels;
    }

    public double getGrow_percentage() {
        return ITestConstants.GROW_PERCENTAGE;
    }

    public double getLeafLength() {
        return this.leafLength;
    }

    public int getAmountOfLeaves() {
        return amountOfLeaves;
    }

    public void setAmountOfLeaves(int amountOfLeaves) {
        this.amountOfLeaves = amountOfLeaves;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }    

    public float getTreeHeight() {
        return treeHeight;
    }

    public void setTreeHeight(float treeHeight) {
        this.treeHeight = treeHeight;
    }
    
    public float getID() {
        return id;
    }

    public int getID() {
        return ID;
    }
    
    @Override
    public String toString() {
        return "TestTree{" + "posX=" + posX + ", length=" + length + ", levels=" + levels + ", leafLength=" + leafLength + ", amountOfLeaves=" + amountOfLeaves + ", score=" + score + ", treeHeight=" + treeHeight + '}';
    }
    
           
}
