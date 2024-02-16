package dev.pinaki.cannaguide.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

private val emojis = listOf(
    "🐶", // Dog
    "🐱", // Cat
    "🐭", // Mouse
    "🐹", // Hamster
    "🐰", // Rabbit
    "🦊", // Fox
    "🐻", // Bear
    "🐼", // Panda
    "🐨", // Koala
    "🐯", // Tiger
    "🦁", // Lion
    "🐮", // Cow
    "🐷", // Pig
    "🐸", // Frog
    "🐵", // Monkey
    "🐔", // Chicken
    "🐧", // Penguin
    "🐦", // Bird
    "🐤", // Baby chick
    "🦆", // Duck
    "🦅", // Eagle
    "🦉", // Owl
    "🦇", // Bat
    "🐺", // Wolf
    "🐗", // Boar
    "🐴", // Horse
    "🦄", // Unicorn
    "🐝", // Honeybee
    "🪱", // Worm
    "🐛", // Bug
    "🦋", // Butterfly
    "🐌", // Snail
    "🐞", // Lady beetle
    "🐜", // Ant
    "🦗", // Cricket
    "🕷️", // Spider
    "🦂", // Scorpion
    "🦟", // Mosquito
    "🪰", // Fly
    "🪲", // Beetle
    "🪳", // Cockroach
    "🦠", // Microbe
    "🌵", // Cactus
    "🎄", // Christmas tree
    "🌲", // Evergreen tree
    "🌳", // Deciduous tree
    "🌴", // Palm tree
    "🌱", // Seedling
    "🌿", // Herb
    "☘️", // Shamrock
    "🍀", // Four leaf clover
    "🎍", // Pine decoration
    "🍂", // Fallen leaf
    "🍃", // Leaf fluttering in wind
    "🍁", // Maple leaf
    "🍄", // Mushroom
    "🌾", // Sheaf of rice
    "💐", // Bouquet
    "🌷", // Tulip
    "🌹", // Rose
    "🥀", // Wilted flower
    "🌺", // Hibiscus
    "🌸", // Cherry blossom
    "🌼", // Blossom
    "🌻", // Sunflower
    "🌞", // Sun with face
    "🌝", // Full moon with face
    "🌛", // First quarter moon with face
    "🌜" // Last quarter moon with face
)


private val flowerEmojis = listOf(
    "🌵", // Cactus
    "🌱", // Seedling
    "🌿", // Herb
    "☘️", // Shamrock
    "🍀", // Four leaf clover
    "🎍", // Pine decoration
    "🍃", // Leaf fluttering in wind
    "🍀", // Clover
)


@Composable
fun rememberRandomEmptyEmoji() = remember {
    emojis.random()
}

@Composable
fun rememberRandomFlowerEmoji() = remember {
    flowerEmojis.random()
}