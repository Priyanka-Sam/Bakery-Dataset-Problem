import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Mobiux {

static	ArrayList<ArrayList<String>> dataset = new ArrayList<ArrayList<String>>();
	static String[] columnName;
	static String[] temp;
	static ArrayList<String> tempList;
	
    static HashMap<String, Integer> popular_item_Jan = new HashMap<String, Integer>(); 
    static HashMap<String, Integer> popular_item_Feb = new HashMap<String, Integer>(); 
    static HashMap<String, Integer> popular_item_Mar = new HashMap<String, Integer>(); 

    static HashMap<String, Integer> revenue_Jan = new HashMap<String, Integer>(); 
    static HashMap<String, Integer> revenue_Feb = new HashMap<String, Integer>(); 
    static HashMap<String, Integer> revenue_Mar = new HashMap<String, Integer>(); 

    static String[] most_popular=new String[3];
    static int[] monthly_sales = new int[3];
	static int total_sales =0;

	static int[] max = new int[3];
	static int[] min = new int[3];
	static int[] avg = new int[3];
	static int[] count = new int[3];
	static int[] quant_sum = new int[3];



		public static void main(String[] args) throws IOException {
			
			//initializing fifth query variables
			Arrays.fill(max,Integer.MIN_VALUE);
			Arrays.fill(min,Integer.MAX_VALUE);
			Arrays.fill(avg, 0);
			Arrays.fill(count, 0);


            load_Data();
            calculate_iteration();
            show_sales();
            get_popular_item();
            get_max_revenue();
            get_min_max_avg();
	}

	
		
	public static void load_Data ()  throws  IOException
	{
		//reading data from file
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\priyanka\\Desktop\\sales_data.txt"));
	    String line = null;
	    line= br.readLine();
        columnName = line.split(",");
     
        //splitting and storing into 2D ArrayList
	    while ((line = br.readLine()) != null) {
	        temp = line.split(",");
	        tempList = new ArrayList<String>(Arrays.asList(temp)); 
	        dataset.add(tempList);

	    }
	    br.close();
	}
	
	//iterating the date set once to get the all required results
	public static void calculate_iteration() 
	{
        for (ArrayList<String> list: dataset) {
    	  String date =list.get(0);
    	  String name = list.get(1);
    	 // int unit_price=Integer.parseInt(list.get(2));
    	  int quant = Integer.parseInt(list.get(3));
    	  int total_price = Integer.parseInt(list.get(4));
    	  
//first query
total_sales += total_price;


//second query
 int month = date_format(date);
monthly_sales[month]+= total_price;


//third query
if(month==0)
{popular_item_Jan.put(name, popular_item_Jan.get(name) == null ? quant : popular_item_Jan.get(name) + quant); }
else if(month==1)
{popular_item_Feb.put(name, popular_item_Feb.get(name) == null ? quant : popular_item_Feb.get(name) + quant); }
else
{popular_item_Mar.put(name, popular_item_Mar.get(name) == null ? quant : popular_item_Mar.get(name) + quant); }


//fourth query
if(month==0)
{revenue_Jan.put(name, revenue_Jan.get(name) == null ? total_price : revenue_Jan.get(name) + total_price); }
else if(month==1)
{revenue_Feb.put(name, revenue_Feb.get(name) == null ? total_price : revenue_Feb.get(name) + total_price); }
else
{revenue_Mar.put(name, revenue_Mar.get(name) == null ? total_price : revenue_Mar.get(name) + total_price); }


//fifth query
if(!(name == most_popular[month]))
{
if(max[month]<quant)
	max[month]=quant;

if(min[month]>=quant)
	min[month]=quant;

avg[month]+=quant;
count[month]++;
}
}
}
	
	
	public static void show_sales()
	{
	    
		System.out.println("Total sales of the store : " + total_sales);
		System.out.println();
		int i=1;
		
	    for(int item:monthly_sales){
	    System.out.println("Monthy sale for 2019-0"+(i)+" : "+item);
		i++;}
	
	    System.out.println();
	}
	
	public static void get_min_max_avg()
	{
		//printing min,max
		//calculating average
		//each month
		for(int i=0;i<3;i++)
		{System.out.println("For 2019-0"+(i+1) +" Most Popular Item : "+ most_popular[i]+" ");
		System.out.println("Min : " +min[i] + " Max : "+max[i] + " Average : "+ (float)avg[i]/count[i]);
		}
	}

	public static void get_popular_item()
	{
	//printing out the items with maximum quantity sold
		System.out.print("Most popular item for 2019-01 : ");
         for (Map.Entry<String, Integer> entry : popular_item_Jan.entrySet()) {
			if (Collections.max(popular_item_Jan.values()).equals(entry.getValue())) {
				most_popular[0]= entry.getKey();
				System.out.println(entry.getKey()+", Quantity : "+entry.getValue());}}
         
		System.out.print("Most popular item for 2019-02 : ");
	    for (Map.Entry<String, Integer> entry : popular_item_Feb.entrySet()) {
			if (Collections.max(popular_item_Feb.values()).equals(entry.getValue())) {
				most_popular[1]= entry.getKey();
				 System.out.println(entry.getKey()+", Quantity : "+entry.getValue());}}
	    
		System.out.print("Most popular item for 2019-03 : ");
		for (Map.Entry<String, Integer> entry : popular_item_Mar.entrySet()) {
			if (Collections.max(popular_item_Mar.values()).equals(entry.getValue())) {
				most_popular[2]= entry.getKey();
				 System.out.println(entry.getKey()+", Quantity : "+entry.getValue());}}
		System.out.println();

	}
	
	public static void get_max_revenue()
	{
		//printing out the items with maximum revenue
		System.out.print("Item generating most Revenue for 2019-01 : ");

		for (Map.Entry<String, Integer> entry : revenue_Jan.entrySet()) {
			if (Collections.max(revenue_Jan.values()).equals(entry.getValue())) {
				 System.out.println(entry.getKey()+", Revenue : "+entry.getValue());}}
		
		System.out.print("Item generating most Revenue for 2019-02 : ");
	    for (Map.Entry<String, Integer> entry : revenue_Feb.entrySet()) {
			if (Collections.max(revenue_Feb.values()).equals(entry.getValue())) {
				 System.out.println(entry.getKey()+", Revenue : "+entry.getValue());}}
	    
		System.out.print("Item generating most Revenue for 2019-03 : ");
		for (Map.Entry<String, Integer> entry : revenue_Mar.entrySet()) {
			if (Collections.max(revenue_Mar.values()).equals(entry.getValue())) {
				 System.out.println(entry.getKey()+", Revenue : "+entry.getValue());}}
		System.out.println();

	}
	public static int date_format(String s)
	{
		//formatting date using Calender Class
		//return month after extraction
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		int month=0;
		try {  
			Date date=simpleDateFormat.parse(s);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			 month = calendar.get(Calendar.MONTH);

		} catch (ParseException e) 
		{e.printStackTrace();}
		return month; 	
	}
	
}
