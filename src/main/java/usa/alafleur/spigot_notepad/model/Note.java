package usa.alafleur.spigot_notepad.model;

import java.util.UUID;

public class Note {
    private long id;
    private UUID playerUUID;
    private String content;

    public Note() {}

    public Note(UUID _playerUUID, String _content) {
        playerUUID = _playerUUID;
        content = _content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean belongsTo(org.bukkit.entity.Entity entity) {
        return entity.getUniqueId().equals(playerUUID);
    }
}
