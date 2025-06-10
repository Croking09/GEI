package e1;

public class StringUtilities {

    public static boolean isValidString(String cadena, String caracteresValidos, int longitudMinima){
        if(cadena!=null && !cadena.isEmpty() && cadena.length()>=longitudMinima){
            for(int x=0; x < cadena.length();x++){
                if(!caracteresValidos.contains(String.valueOf(cadena.charAt(x))) && !Character.isDigit(cadena.charAt(x))){
                    return false;
                }
            }
            return true;
        }
        else{
            return false;
        }
    }

    public static String lowercaseFirst(String cadena){
        StringBuilder minusculas=new StringBuilder();
        StringBuilder mayusculas=new StringBuilder();

        for(int i=0;i<cadena.length();i++){
            char c=cadena.charAt(i);
            if(Character.isLowerCase(c)){
                minusculas.append(c);
            }
            else{
                mayusculas.append(c);
            }
        }
        return minusculas.append(mayusculas).toString();
    }

    public static boolean checkTextStats(String cadena, int min, int max){
        if(cadena==null || cadena.isEmpty() || min<=0 || max<=0){
            throw new IllegalArgumentException("Argumentos no validos");
        }

        String[] palabras =cadena.split(" ");
        double media=0;
        int longest=palabras[0].length();

        for(String word : palabras) {
            if (word.length() > longest) {
                longest = word.length();
            }
            media += word.length();
        }
        media/= palabras.length;

        return !(media < min) && !(media > max) && !(longest > 2 * media);
    }
}