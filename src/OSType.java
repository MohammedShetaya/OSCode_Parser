public class OSType {

    private Object data ;
    private String type ;

    public OSType (String s) {
        try {
            data = (Integer) Integer.parseInt(s) ;
        }catch (Exception e){
            data = s ;
        }
        type = data.getClass().getName() ;
    }

    public boolean isInteger () {
        return type.equals(Integer.class.getName()) ;
    }

    public String getType () {
        return type ;
    }
    public boolean equalType (OSType o) {
        return this.type.equals(o.getType()) ;
    }
    public Object getData() {
        return data;
    }

    public OSType add (OSType o) {
        if (this.isInteger()){
            this.data =  Integer.valueOf(((Integer)this.data).intValue() + ((Integer)o.getData()).intValue() ) ;
        }
        else
            this.data = ((String) this.data) + ((String) o.getData()) ;
        return this;
    }
}
