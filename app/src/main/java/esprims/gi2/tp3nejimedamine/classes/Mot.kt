package esprims.gi2.tp3nejimedamine.classes

class Mot(var leMot: String, var taille: Int, var type: String, var genre: String) {

    override fun toString(): String {
        return "leMot='$leMot', taille=$taille, type='$type', genre='$genre'"
    }
}