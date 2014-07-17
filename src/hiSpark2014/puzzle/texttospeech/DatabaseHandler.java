package hiSpark2014.puzzle.texttospeech;





import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler {

	public static final String DataBase_Name="Puzzle1";
	public static final int DATABASE_VERSION=2;
	public static final String Puzzle_Table="Puzzle_Table";

	public static final String Username="username";

	public static final String Create_Puzzle_table="create table " + Puzzle_Table + "(username text);";


	DataBaseHelper dbhelper;
	Context ctx;
	SQLiteDatabase db;

	public DatabaseHandler(Context ctx)
	{
		this.ctx=ctx;
		dbhelper=new DataBaseHelper(ctx);
	}


	private static class DataBaseHelper extends SQLiteOpenHelper
	{
		public DataBaseHelper(Context ctx)
		{
			super(ctx,DataBase_Name,null,DATABASE_VERSION);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			db.execSQL("DROP TABLE IF EXISTS " + Puzzle_Table);

			onCreate(db);
		}

		@Override
		public void onCreate(SQLiteDatabase db) 
		{		
			try 
			{
				db.execSQL(Create_Puzzle_table);
				ContentValues content=new ContentValues();
				content.put(Username, "null");

			db.insertOrThrow(Puzzle_Table, null, content);

			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void close()
	{
		dbhelper.close();
	}

	public DatabaseHandler open() {
		db=dbhelper.getWritableDatabase();
		return this;
	}

//	public long insertName(String name)
//	{
//		long id = 0;
//		try{
//			open();
//			ContentValues content=new ContentValues();
//			content.put(Username, name);
//
//			id = db.insertOrThrow(Puzzle_Table, null, content);
//		}catch(Exception ex){
//			Log.d("EXCEPTION", ex.getMessage());
//		}		
//		return id;
//	}

	public Cursor getname() {
		// TODO Auto-generated method stub
		return db.rawQuery("select username from Puzzle_Table", null) ;
	}

	public void updatename(String name1) {
//		try{
//		this.open();
//		String selectQuery ="UPDATE Puzzle_Table SET username='"+text+"'";
//		Cursor cursor =db.rawQuery(selectQuery, null);
//		System.out.println("updated1:"+cursor.getCount());
//		}catch(Exception ex){
//			Log.i("DatabaseError", ex.getMessage());
//		}
	    
		ContentValues values = new ContentValues();
		values.put(Username, name1);
		db.update(Puzzle_Table, values, null, null);
	}

}
