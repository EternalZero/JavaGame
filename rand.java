import java.util.Random;

class rand{

public static void main(String args[]){

for(int x= 0 ; x<100; x++){
	Random rn = new Random();
	int answer = rn.nextInt(4) + 1;
	
	System.out.println(answer);

}

}
}