public class Player {
    private String name;
    private Weapon[] rightHand;
    private Weapon[] leftHand;
    private int hp;
    private int fp;
    private int stamina;
    private String encumberment;
    private int[] stats;
    public static int runes = 15;
    
    public Player(String nm) {
        this.name = nm;
        rightHand = new Weapon[3];
        for(int i = 0; i<3; i++) {
            this.rightHand[i] = new Weapon();
        }
        leftHand = new Weapon[3];
        for(int i = 0; i<3; i++) {
            this.leftHand[i] = new Weapon();
        }
        this.hp = 300;
        this.fp = 50;
        this.stamina = 80;
        this.encumberment = "Light Equip Load";
        stats = new int[8];
        for(int i = 0; i<8; i++) {
            this.stats[i] = 0;
        }
    }
    
//START GETTERS AND SETTERS
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String nm){
        this.name = nm;
    }
    
    public Weapon getRightHand(int i){
        return this.rightHand[i];
    }
    
    public void setRightHand(Weapon rghtHnd, int i){
        this.rightHand[i] = rghtHnd;
    }
    
    public Weapon getLeftHand(int i){
        return this.rightHand[i];
    }
    
    public void setLeftHand(Weapon lfthnd, int i){
        this.leftHand[i] = lfthnd;
    }
    
    public int getHp(){
        return this.hp;
    }
    
    public void setHp(int hp){
        this.hp = hp;
    }
    
    public int getFp(){
        return this.fp;
    }
    
    public void setFp(int fp){
        this.fp = fp;
    }
    
    public int getStamina(){
        return this.stamina;
    }
    
    public void setStamina(int stmn){
        this.stamina = stmn;
    }
    
    public String getEncumberment(){
        return this.encumberment;
    }
    
    public void setEncumberment(String ncmbrmnt){
        this.encumberment = ncmbrmnt;
    }
    
    public int[] getStats(){
        return this.stats;
    }
    
    public int getStats(int i){
        return this.stats[i];
    }
    
    public void setStats(int[] stts){
        this.stats = stts;
    }
    
    public void setStats(int stts, int i){
        this.stats[i] = stts;
    }
//END GETTERS AND SETTERS

//TOSTRING()
    public String toString() {
        return ("Name: " + name + "\nRight Hand: " + rightHand + "\nLeft Hand: " + leftHand + "\nHP: " + hp + "\nFP: " + fp + "\nStamina: " + stamina + "\nEncumberment: " + encumberment + "\nVigor Stat: " + stats[0] + "\nMind Stat: " + stats[1] + "\nEndurance Stat: " + stats[2] + "\nStrength Stat: " + stats[3] + "\nDexterity Stat: " + stats[4] + "\nIntelligence Stat: " + stats[5] + "\nFaith Stat: " + stats[6] + "\nArcane Stat: " + stats[7] + "\nRunes: " + runes);
    }
}
