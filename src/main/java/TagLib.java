

import java.util.ArrayList;

public class TagLib {
    private String[] tags;
    private int numTags;
    private int capacity;

    public TagLib(){
        tags = new String[20];
        numTags = 0;
        capacity = 20;
    }
    public String printTags(){
        String tagStr = "Tags:\n";
        for(int x = 0; x < numTags; x ++){
            tagStr += tags[x] + "\n";
        }
        return tagStr;
    }
    public void addTag(String tagIn) throws IllegalArgumentException{
        if(!validTag(tagIn)) throw new IllegalArgumentException("Not Valid Tag");
        if(isTag(tagIn)) throw new IllegalArgumentException("Duplicate tag");
        if(numTags>= capacity) this.doubleLength();
        String temp = tagIn;
        boolean placed = false;
        if(numTags <= 0){
            tags[0] = tagIn;
            numTags++;
            return;
        }
        for(int x = 0; x < numTags; x++){
            if(!placed){
                if(alphabetical(tagIn, tags[x]) == -1){
                    placed = true;
                    temp = tags[x];
                    tags[x] = tagIn;
                }
            }
            else{
                tagIn = tags[x];
                tags[x] = temp;
                temp = tagIn;
            }
        }
        tags[numTags] = temp;
        numTags++;
    }

    private boolean isTag(String tagIn, int index, int size){
        if(size < 1) return false;
        else if (size == 1){
            if (tags[index].equals(tagIn))return true;
            return false;
        }
        else if (size == 2){
            if(tags[index].equals(tagIn))return true;
            else if(tags[index+1].equals(tagIn))return true;
            return false;
        }
        else{
            if(tags[index].equals(tagIn)) return true;
            if(alphabetical(tagIn, tags[index]) == -1)
                return isTag(tagIn, index - ((size + 1)/4), (size-1)/2);
            else
                return isTag(tagIn, index + ((size + 1)/4), size /2);

        }
    }


    public boolean isTag(String tagIn) throws IllegalArgumentException{
        if(!validTag(tagIn)) throw new IllegalArgumentException("Not valid tag");
        return isTag(tagIn, (numTags-1)/2,numTags);

    }

    private int subTagRange(String tagIn, int index, int size){
        if(size < 1) return -1;
        boolean found = true;
        if(tagIn.length() > tags[index].length()) found = false;
        else{
            for (int x = 0; x < tagIn.length(); x ++){
                if(tagIn.charAt(x) != tags[index].charAt(x))
                    found = false;
            }
        }
        if(found)return index;
        if (size == 1){
            return -1;
        }
        else if (size == 2){
            return subTagRange(tagIn, index + 1, size -1);
        }
        else{
            if(alphabetical(tagIn, tags[index]) == -1)
                return subTagRange(tagIn, index - ((size + 3)/4), (size-1)/2);
            else
                return subTagRange(tagIn, index + ((size + 3)/4), size /2);

        }
    }

    private int[] subTagRange(String subTag){
        int middleInd = subTagRange(subTag, (numTags-1)/2,numTags);
        if(middleInd == -1) return null;
        int[] indexes = new int[2];
        indexes[0] = middleInd;
        indexes[1] = middleInd;
        here:{
            for(int x = middleInd; x >= 0; x--){
                if(subTag.length() > tags[x].length()) break;
                for(int y = 0; y < subTag.length(); y++){
                    if(subTag.charAt(y) != tags[x].charAt(y))break here;
                }
                indexes[0] = x;

            }
        }
        break2:{
            for(int x = middleInd; x < numTags; x++){
                if(subTag.length() > tags[x].length()) break;
                for(int y = 0; y < subTag.length(); y++){
                    if(subTag.charAt(y) != tags[x].charAt(y))break break2;
                }
                indexes[1] = x;
            }
        }
        return indexes;
    }

    public String[] search(String subTag) throws IllegalArgumentException{
        if(!validTag(subTag))throw new IllegalArgumentException("Not valid tag");
        int[] indexes = subTagRange(subTag);
        if(indexes == null)return null;
        String[] subList = new String[(indexes[1] - indexes[0]) + 1];
        for(int x = 0; x < indexes[1] - indexes[0] + 1; x++){
            subList[x] = tags[x + indexes[0]];
        }
        return subList;
    }
    public int numTags(){
        return numTags;
    }
    private void doubleLength(){
        String[] temp = tags;
        capacity*=2;
        tags = new String[capacity];
        for(int x = 0; x < numTags; x++){
            tags[x] = temp[x];
        }
    }

    public static boolean validTag(String tag){
        if(tag.length() <= 0) return false;
        for(int x = 0; x < tag.length(); x ++){
            char temp = tag.charAt(x);
            if(!((temp > 64 && temp < 91) || (temp > 96 && temp < 123)))
                return false;
            if(temp == ' ')
                return false;
        }
        return true;
    }

    public static int alphabetical(String lhs, String rhs){
        if(lhs.length() == rhs.length()){
            for(int x = 0; x < lhs.length(); x ++){
                if(lhs.charAt(x) < rhs.charAt(x)){
                    return -1;
                }
                if(lhs.charAt(x) > rhs.charAt(x)){
                    return 1;
                }
            }
            return 0;
        }
        else{
            for(int x = 0; x < (lhs.length() < rhs.length() ? lhs.length():rhs.length()); x++){
                if(lhs.charAt(x) < rhs.charAt(x)){
                    return -1;
                }
                if(lhs.charAt(x) > rhs.charAt(x)){
                    return 1;
                }
            }
            return (lhs.length() < rhs.length() ?  -1 : 1);
        }
    }


}
