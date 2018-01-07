package com.eter.cake;

public class Constants {
	public static final String ORDER_ASC = "1";
	public static final String ORDER_DESC = "0";
	public static final String STORAGE_TYPE_INTERNAL = "1";
	public static final String STORAGE_TYPE_EXTERNAL = "2";
	public static final String BOX_SIZE_SMALL = "1";
	public static final String BOX_SIZE_MEDIUM = "2";
	public static final String BOX_SIZE_LARGE = "3";
	
	public final class Response{
		public static final String SUCCESS = "00";
		public static final String SUCCESS_DESC = "Success";
		public static final String SUCCESS_PARTIAL = "01";
		public static final String SUCCESS_PARTIAL_DESC = "Success Partial";
		public static final String FAILED = "X06";
		public static final String FAILED_DESC = "Failed";

		private Response(){

		}
	}
    
    public static final class ActiveFlag{
		public static final Integer ACTIVE = 1;
		public static final Integer INACTIVE = 0;

		private ActiveFlag(){
			
		}
	}
    
    public final class Message{
		public static final String NOT_FOUND = "not found";
		public static final String NULL_DATA = "Null Data";
		public static final String FAILED_GET = "Failed delete";
		public static final String FAILED_ADD = "Failed add";
		public static final String FAILED_UPDATE = "Failed update";
		public static final String FAILED_DELETE = "Failed delete";

		private Message(){
			
		}
	}
    
    public final class Status{
		public static final String NEW_BOX = "0";
		public static final String NEW_BOX_FILLED_DOC = "1";
		public static final String NEW_BOX_READY_TO_PICKUP = "2";
		public static final String NEW_BOX_AT_OFFICE = "3";
		public static final String BOX_AT_VENDOR = "4";
		public static final String BOX_AT_OFFICE = "5";
		public static final String BOX_OPEN = "6";
		public static final String BOX_CLOSED = "7";
		public static final String BOX_ON_LENDING = "9";
		public static final String BOX_PICKUP_DONE = "9";

		private Status(){
			
		}
	}

	public final class InventoryType{
		public static final String PURCHASE = "PU";
		public static final String STOCK_OPNAME = "SO";
		public static final String REPACKING = "RP";
		public static final String DAMAGE = "DM";
	}
}
