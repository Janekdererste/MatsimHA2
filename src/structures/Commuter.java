package structures;

public class Commuter {

	private int id;
	private int birthYear;
	private Sex sex;
	private int income;
	private int livingZone;
	private int workingZone;
	private int municipalityID;
	private boolean hasDriversLiscence;
	private Housingtype housingtype;
	private int hhChildren;
	private int hhAdults;
	private boolean isCarAvailable;
	private int hhCars;
	
	
	public enum Housingtype{ SingleFamily, MultiFamily };
	public enum Sex{ Male, Female }
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id)
	{
		this.id = Integer.parseInt(id);
	}
	public int getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}
	
	public void setBirthYear(String birthYear)
	{
		this.birthYear = Integer.parseInt(birthYear);
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	public void setSex(String sex)
	{
		if(sex == "0")
			this.sex = Sex.Male;
		else
			this.sex = Sex.Female;
	}
	public int getIncome() {
		return income;
	}
	public void setIncome(int income) {
		this.income = income;
	}
	
	public void setIncome(String income)
	{
		this.income = Integer.parseInt(income);
	}
	
	public int getLivingZone() {
		return livingZone;
	}
	
	public void setLivingZone(int livingZone) {
		this.livingZone = livingZone;
	}
	
	public void setLivingZone(String livingZone)
	{
		this.livingZone = Integer.parseInt(livingZone);
	}
	
	public int getWorkingZone() {
		return workingZone;
	}
	public void setWorkingZone(int workingZone) {
		this.workingZone = workingZone;
	}
	
	public void setWorkingZone(String workingZone)
	{
		this.workingZone = Integer.parseInt(workingZone);
	}
	
	public int getMunicipalityID() {
		return municipalityID;
	}
	
	public void setMunicipalityID(int municipalityID) {
		this.municipalityID = municipalityID;
	}
	
	public void setMunicipalityID(String municipalityID)
	{
		this.municipalityID = Integer.parseInt(municipalityID);
	}
	
	public boolean isHasDriversLiscence() {
		return hasDriversLiscence;
	}
	public void setHasDriversLiscence(boolean hasDriversLiscence) {
		this.hasDriversLiscence = hasDriversLiscence;
	}
	
	public void setHasDriversLiscence(String hasDirversLiscence)
	{
		if(hasDirversLiscence == "0")
			this.hasDriversLiscence = false;
		else
			this.hasDriversLiscence = true;			
	}
	
	public Housingtype getHousingtype() {
		return housingtype;
	}
	public void setHousingtype(Housingtype housingtype) {
		this.housingtype = housingtype;
	}
	
	public void setHousingtype(String housingtype)
	{
		if(housingtype == "0")
			this.housingtype = Housingtype.SingleFamily;
		else
			this.housingtype = Housingtype.MultiFamily;
	}
	public int getHhChildren() {
		return hhChildren;
	}
	public void setHhChildren(int hhChildren) {
		this.hhChildren = hhChildren;
	}
	
	public void setHhChildren(String hhChildren)
	{
		this.hhChildren = Integer.parseInt(hhChildren);
	}
	public int getHhAdults() {
		return hhAdults;
	}
	public void setHhAdults(int hhAdults) {
		this.hhAdults = hhAdults;
	}
	
	public void setHhAdults(String hhAdults)
	{
		this.hhAdults = Integer.parseInt(hhAdults);
	}
	
	public boolean isCarAvailable() {
		return isCarAvailable;
	}
	public void setCarAvailable(boolean isCarAvailable) {
		this.isCarAvailable = isCarAvailable;
	}
	
	public void setCarAvailable(String isCarAvailable)
	{
		if(isCarAvailable == "0")
			this.isCarAvailable = false;
		else
			this.isCarAvailable = true;
	}
	public int getHhCars() {
		return hhCars;
	}
	public void setHhCars(int hhCars) {
		this.hhCars = hhCars;
	}
	
	public void setHhCars(String hhCars)
	{
		this.hhCars = Integer.parseInt(hhCars);
	}
}
