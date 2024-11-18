public class AppController {
    private final AppModel model;

    public AppController(AppModel model) {
        this.model = model;
    }

    // other method
    public boolean stringNotNull(String string){
        if (string.isBlank()){
            return false;
        } else {
            return true;
        }
    }

    public boolean realInt(String string){
        try{
           int age = Integer.parseInt(string); 
           return true;
        } catch (Exception e){
            return false;
        }
    }

    private int convertStringToInt(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        if ("-".equals(s)) {
            return 0;
        }
        return Integer.parseInt(s); // Convert string into integer
    }
}
