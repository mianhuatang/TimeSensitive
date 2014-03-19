package Genetic;
class CPV{
	double[] parameters;
	int isSelected;
	double selectProbability;
	double fittness;
	double exceptProbability;
}
public class Genetic {
	private int popSize = 30; //种群数量
	private int maxgens = 30; //迭代次数
	private double crossProbability = 0.75; //交叉概率
	private double multationProbablility = 0.1; //变异概率
	private int parameterNumber=4;
	private int range = 2000; //用于判断何时停止的数组区间
	private CPV[] CPVs=new CPV[popSize];//初始种群
	
	public Genetic(){
		for(int i=0;i<popSize;i++){
			CPVs[i]=new CPV();
			CPVs[i].parameters=new double[parameterNumber];
			for(int j=0;j<parameterNumber;j++){
				double data = Math.random();
				CPVs[i].parameters[j] = data;
			}
			CPVs[i].fittness=0;
			CPVs[i].isSelected=0;
			CPVs[i].selectProbability=0;
			CPVs[i].exceptProbability=0;
		}
	}
	public void crossover() {
		int x;
		int y;
		int pop = (int)(popSize* crossProbability /2);
		while(pop>0){
			x = (int)(Math.random()*popSize);
			y = (int)(Math.random()*popSize);
			executeCrossover(x,y);//x y 两个体执行交叉
			pop--;
		}
	}
	
	/**
	* 执行交叉函数
	* @param 个体x
	* @param 个体y
	* 对个体x和个体y执行佳点集的交叉，从而产生下一代城市序列
	*/
	private void executeCrossover(int x,int y){
		for(int i=0;i<parameterNumber;i++){
			double p=Math.random();
			if(p>=0&&p<0.5){//0,取第二个的值
				double temp=CPVs[x].parameters[i];
				CPVs[x].parameters[i]=CPVs[y].parameters[i];
				CPVs[y].parameters[i]=temp;
			}
			
		}
	}
	
	/**
	 * 突变
	 */
	public void mutate(){
		for(int i=0;i<popSize;i++){
			double random=Math.random();
			if(random<=multationProbablility){//发生突变
				for(int j=0;j<parameterNumber;j++){
					double r=Math.random();
					if(r<0.5){//+0.01
						CPVs[i].parameters[j]+=0.01;
					}
					else{//-0.01
						CPVs[i].parameters[j]-=0.01;
					}
				}
				
			}
		}
	}
	/**
	 * 计算适应度
	 */
	private void CalFitness() {
		for (int i = 0; i < popSize; i++) {
			CPVs[i].fittness=0;
		}
	}
	/**
	 * 计算选择概率
	 */
	private void CalSelectP(){
		long sum = 0;
		for( int i = 0; i< popSize; i++)
			sum += CPVs[i].fittness;
		for( int i = 0; i< popSize; i++)
			CPVs[i].selectProbability = (double)CPVs[i].fittness/sum;
	}
	/**
	 * 计算期望概率
	 */
	private void CalExceptP(){
		for( int i = 0; i< popSize; i++)
			CPVs[i].exceptProbability = (double)CPVs[i].selectProbability * popSize;
	}
	/**
	 * 进行选择
	 */
	private void CalIsSelected(){
		int needSelecte = popSize;
		for( int i = 0; i< popSize; i++)
		if( CPVs[i].exceptProbability<1){
			CPVs[i].isSelected++;
			needSelecte --;
		}
		double[] temp = new double[popSize];
		for (int i = 0; i < popSize; i++) {
			temp[i] = CPVs[i].exceptProbability*10;
		}
		int j = 0;
		while (needSelecte != 0) {
			for (int i = 0; i < popSize; i++) {
				if ((int) temp[i] == j) {
					CPVs[i].isSelected++;
					needSelecte--;
					if (needSelecte == 0)
						break;
				}
			}
			j++;
		}
	}
	public void pad(){
		int best = 0;
		int bad = 0;
		while(true){ 
			while(CPVs[best].isSelected <= 1 && best<popSize-1)
				best ++;
			while(CPVs[bad].isSelected != 0 && bad<popSize-1)
				bad ++;
			for(int i = 0; i< parameterNumber; i++)
				CPVs[bad].parameters[i] = CPVs[best].parameters[i];
			CPVs[best].isSelected --;
			CPVs[bad].isSelected ++;
			bad ++; 
			if(best == popSize ||bad == popSize)
				break;
		}
		}
	/**
	 * 计算每个个体的适应度，选择概率
	 */
	public void CalAll(){
		for( int i = 0; i< popSize; i++){
			CPVs[i].fittness = 0;
			CPVs[i].selectProbability = 0;
			CPVs[i].exceptProbability = 0;
			CPVs[i].isSelected = 0;
		}
		CalFitness();
		CalSelectP();
		CalExceptP();
		CalIsSelected();
		}
	/**
	 * 算法执行
	 */
	public void run(){
		double[] result = new double[range];
		//result初始化为所有的数字都不相等
		for( int i = 0; i< range; i++)
			result[i] = i;
		int index = 0; //数组中的位置
		int num = 1; //第num代
		while(maxgens>0){
			System.out.println("----------------- 第 "+num+" 代 -------------------------");
			CalAll();
			pad();
			crossover();
			mutate();
			maxgens --;
			double temp = CPVs[0].fittness;
			for ( int i = 1; i< popSize; i++)
				if(CPVs[i].fittness<temp){
					temp = CPVs[i].fittness;
			}
			System.out.println("最优的解："+temp);
			result[index] = temp;
//			if(isSame(result))
//				break;
			index++;
			if(index==range)
				index = 0;
			num++;
		}
	}
}


