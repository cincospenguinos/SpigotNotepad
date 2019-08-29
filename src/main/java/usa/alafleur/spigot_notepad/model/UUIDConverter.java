package usa.alafleur.spigot_notepad.model;

import io.objectbox.converter.PropertyConverter;

import java.util.UUID;

public class UUIDConverter implements PropertyConverter<UUID, String> {
    @Override
    public UUID convertToEntityProperty(String string) {
        return UUID.fromString(string);
    }

    @Override
    public String convertToDatabaseValue(UUID uuid) {
        return uuid.toString();
    }
}
