package sample;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DataModel implements CEventDAO {

    ArrayList<CEvent> cEventDataList;


DataModel(){
    cEventDataList = new ArrayList<>();
    try{ getListFromDataBase(cEventDataList); } catch (SQLException e){e.printStackTrace();}
    updatePriority();

}


    public ArrayList<CEvent> getcEventDataList(){
        return cEventDataList;
    }


public ArrayList<CEvent> getListByPredicate(Predicate<CEvent> p){
    ArrayList<CEvent> list;
    list= (ArrayList<CEvent>) cEventDataList.stream().filter(p).collect(Collectors.toList());
    return list;
}


public void updatePriority(){
    cEventDataList.stream().forEach(CEvent::choosePriority);
}


public boolean addCEvent(CEvent e){
    if(e ==null) return false;
    else {
        cEventDataList.add(e);
        try {
            insert(e);
        }catch (SQLException f){
            f.printStackTrace();
        }
        return true;
    }
}
    public boolean deleteCEvent(CEvent e){
        if(e ==null) return false;
        else {
            cEventDataList.remove(e);
            return true;
        }
    }

    public boolean editCEvent(CEvent e){
        if(e ==null) return false;
        else {
            try {
                update(e);
            }catch (SQLException f){
                f.printStackTrace();
            }
            return true;
        }
    }

    public boolean saveToFile(String fileName, List<CEvent> cEventList) throws IOException {
        if(fileName == null || cEventList == null) throw new NullPointerException();
        boolean isCorrect = true;
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(cEventList);
        oos.close();
        return isCorrect;
    }


    public ArrayList<CEvent> readFromFile(String fileName) throws IOException, ClassNotFoundException, NullPointerException {
        ArrayList<CEvent> list = new ArrayList<>();
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        list = (ArrayList<CEvent>) ois.readObject();
        ois.close();
        return  list;

    }

}
