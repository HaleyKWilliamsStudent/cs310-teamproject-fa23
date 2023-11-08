package edu.jsu.mcis.cs310.tas_fa23;

import java.util.zip.CRC32;

public class Badge {

    private final String id, description;

    public Badge(String id, String description) {
        this.id = id;
        this.description = description;
    }
    
    public Badge(String description) {
        this.description = description;
        
        CRC32 crc32 = new CRC32();
                
        crc32.update(description.getBytes());
        
        StringBuilder newId = new StringBuilder();
        
        String generated = Long.toHexString(crc32.getValue()).toUpperCase();
        
        if (generated.length() < 8) {
            int amount = 8 - generated.length();
            
            for (int i = 0; i < amount; i++) {
                newId.append("0");
            }
        }
        
        newId.append(generated);
        
        this.id = newId.toString();
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append('#').append(id).append(' ');
        s.append('(').append(description).append(')');

        return s.toString();

    }

}
