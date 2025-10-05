public class Main {
    public static void main (String[] args){
        String[] words = {"flower","flow","flight"};

        StringBuilder prefix = LcommonPrefix(words);



        System.out.println("Common prefix: " + prefix);


    }

    public static StringBuilder LcommonPrefix(String[] words){

        StringBuilder prefix = new StringBuilder();

        int index = 0;
        boolean con = true;

        while (con){
            if (index < words[0].length()){
                char c = words[0].charAt(index);

                for (int i = 1; i < words.length; i++){
                    if (index >= words[i].length() || words[i].charAt(index) != c){
                        con = false;
                        break;
                    }
                } if (con){
                    prefix.append(c);
                    index++;
                } else {
                    break;
                }

            }
        }





       return prefix;
    }
}
