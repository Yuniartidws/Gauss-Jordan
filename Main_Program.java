import java.util.Scanner;

public class Main_Program{
	static GeneralMatriks x1 = new GeneralMatriks();
	static HilbertMatriks x2 = new HilbertMatriks();
	static CircuitMatriks x3 = new CircuitMatriks();
	static MatriksHampiran x4 = new MatriksHampiran();
	static InterpolationMatriks x5 = new InterpolationMatriks();
	static int x;
	static char y;
	public static void main(String args[]){
		Scanner sin = new Scanner(System.in);
		

System.out.println("   _____                            ______ _ _           _             _   _             "); 
System.out.println("  / ____|                          |  ____| (_)         (_)           | | (_)            "); 
System.out.println(" | |  __  __ _ _   _ ___ ___       | |__  | |_ _ __ ___  _ _ __   __ _| |_ _  ___  _ __        "); 
System.out.println(" | | |_ |/ _` | | | / __/ __|      |  __| | | | '_ ` _ \\| | '_ \\ / _` | __| |/ _ \\| '_ \\     ");  
System.out.println(" | |__| | (_| | |_| \\__ \\__ \\      | |____| | | | | | | | | | | | (_| | |_| | (_) | | | |     "); 
System.out.println("  \\_____|\\__,_|\\__,_|___/___/      |______|_|_|_| |_| |_|_|_| |_|\\__,_|\\__|_|\\___/|_| |_|      "); 
System.out.println(" | |            _                                                                                      ");                      
System.out.println(" | |__  _   _  (_)                                                                                     ");                      
System.out.println(" | '_ \\| | | |                                                                                        ");                       
System.out.println(" | |_) | |_| |  _                                                                                      ");                      
System.out.println(" |_.__/ \\__, | (_)                                                                                    ");                        
System.out.println("         __/ |                                                                                         ");                       
System.out.println("  _ _ _ |___/        _                         __  __       _ _    _     _  _ _                        ");                       
System.out.println(" ( | ) |/ /         (_)                       |  \\/  |     | | |  (_)   | |( | )                      ");                        
System.out.println("  V V| ' / ___ _ __  _ _ __   __ _  __ _ _ __ | \\  / | __ _| | | ___ ___| |_V V                       ");                       
System.out.println("     |  < / _ \\ '_ \\| | '_ \\ / _` |/ _` | '_ \\| |\\/| |/ _` | | |/ / / __| __|                     ");                            
System.out.println("     | . \\  __/ |_) | | | | | (_| | (_| | | | | |  | | (_| | |   <| \\__ \\ |_                        ");                           
System.out.println("     |_|\\_\\___| .__/|_|_| |_|\\__, |\\__,_|_| |_|_|  |_|\\__,_|_|_|\\_\\_|___/\\__|          ");                                       
System.out.println("              | |             __/ |                                                        ");                                   
System.out.println("              |_|            |___/                                                         \n\n");  

System.out.println("-----------------------13519030  Yonas Adiel Wiguna-----------------------");
System.out.println("-----------------------13519096       Ensof Barhami-----------------------");
System.out.println("-----------------------13519155 Restu Wahyu Kartiko-----------------------\n\n");
		do{
			System.out.println("----Menu----");
			System.out.println("1) Sistem Persamaan Linear Umum");
			System.out.println("2) Sistem Persamaan Linear dengan matriks Hilbert");
			System.out.println("3) Pemecahan Permasalahan Rangkaian Listrik");
			System.out.println("4) Hampiran fungsi");
			System.out.println("5) Interpolasi titik-titik pada koordinat kartesius");
			System.out.println("999) KELUAR");
			System.out.print("\nPilih menu :\n[1/2/3/4/5/999] : ");

			x = sin.nextInt();
			if(x==1){
				x1.run1();
			} else if(x==2){
				x2.run2();
			} else if(x==3){
				x3.run3();
			} else if(x==4){
				x4.run4();
			} else if(x==5){
				x5.run5();
			} else if(x==999){
				System.out.println("\n\nProgram dihentikan\n\n");
				 

			System.out.println("	  	 _______        _                 _             _ _     ");
 			System.out.println("		|__   __|      (_)               | |           (_) |    ");
    		System.out.println("		   | | ___ _ __ _ _ __ ___   __ _| | ____ _ ___ _| |__  ");
    		System.out.println("		   | |/ _ \\ '__| | '_ ` _ \\ / _` | |/ / _` / __| | '_ \\ ");
			System.out.println("		   | |  __/ |  | | | | | | | (_| |   < (_| \\__ \\ | | | |");
    		System.out.println("		   |_|\\___|_|  |_|_| |_| |_|\\__,_|_|\\_\\__,_|___/_|_| |_|");

                                                         
			} else {
				System.out.println("Pilihan tidak tersedia\n\n");
			}
			System.out.println("Apakah ingin melakukan perhitungan lain ? [y/N]");
			String inputSaveToFile = sin.next();
			if(inputSaveToFile.equals("y")){
				System.out.print("\033[H\033[2J");
				System.out.flush();
			}
		}while(x!=999 && y!= 'N');
	}
}