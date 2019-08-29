package usa.alafleur.spigot_notepad.model;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Index;

import java.util.UUID;

@Entity
public class Note {

    @Id
    private long id;

    @Index
    private String name;

    @Convert(converter = UUIDConverter.class, dbType = String.class)
    private UUID playerUUID;

    private String content;

    public Note() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }
}
