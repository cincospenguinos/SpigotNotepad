# SpigotNotepad

A simple notepad application for Spigot. Fast, sleek, simple, intuitive.

## Installing

Simply take the built jar file and place it into your `plugins` directory.

## Usage

There are four different parts of the `/note` command that are included: `read`, `delete`, `list`, and `add`.

### Adding a note

To add a note, simply type `/note add` and then type in the rest of your note's content:

`/note add Hello there. This is a note to myself to go and mine some diamonds later.`

### Listing notes

To list your notes, simply type `/note list`. This will print out the first few characters of each note as well as their IDs.

### Reading or Deleting notes

To read or delete a note, simply include its ID:

`/note read 12`
`/note delete 3`

## Contributing

1. Fork this repo
2. Write tests for the behavior you want adjusted (trust me--doing it first is the best way to go.)
3. Implement behavior
4. Check and make sure the tests pass.
5. Refactor. Repeat until behavior is finished.
6. Submit a PR.
