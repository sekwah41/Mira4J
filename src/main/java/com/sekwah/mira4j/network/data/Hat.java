package com.sekwah.mira4j.network.data;

public enum Hat {

    NONE(0),
    ASTRONAUT(1),
    BASEBALL_CAP(2),
    BRAIN_SLUG(3),
    BUSH_HAT(4),
    CAPTAIN_HAT(5),
    DOUBLE_TOP_HAT(6),
    FLOWERPOT(7),
    GOGGLES(8),
    HARD_HAT(9),
    MILITARY_HAT(10),
    PAPER_HAT(11),
    PARTY_HAT(12),
    POLICE_HAT(13),
    STETHOSCOPE(14),
    TOP_HAT(15),
    TOWEL_WIZARD(16),
    USHANKA(17),
    VIKING(18),
    WALL_GUARD_CAP(19),
    SNOWMAN(20),
    REINDEER_ANTLERS(21),
    CHRISTMAS_LIGHTS(22),
    SANTA_HAT(23),
    CHRISTMAS_TREE(24),
    CHRISTMAS_PRESENT(25),
    CANDY_CANES(26),
    ELF_HAT(27),
    NEW_YEARS_2018(28),
    WHITE_HAT(29),
    CROWN(30),
    EYEBROWS(31),
    HALO(32),
    HERO_CAP(33),
    PIP_CAP(34),
    PLUNGER(35),
    SCUBA_MASK(36),
    HENRY_STICKMIN(37),
    STRAW_HAT(38),
    TEN_GALLON_HAT(39),
    THIRD_EYE(40),
    TOILET_PAPER(41),
    TOPPAT_CLAN_LEADER(42),
    BLACK_FEDORA(43),
    SKI_GOGGLES(44),
    HEARING_PROTECTION(45),
    HAZMAT_MASK(46),
    FACE_MASK(47),
    SECURITY_HAT_GLASSES(48),
    SAFARI_HAT(49),
    BANANA(50),
    BEANIE(51),
    BEAR_EARS(52),
    CHEESE(53),
    CHERRY(54),
    EGG(55),
    GREEN_FEDORA(56),
    FLAMINGO(57),
    FLOWER(58),
    KNIGHT_HELMET(59),
    PLANT(60),
    BAT_EYES(61),
    BAT_WINGS(62),
    HORNS(63),
    MOHAWK(64),
    PUMPKIN(65),
    SCARY_PAPER_BAG(66),
    WITCH_HAT(67),
    WOLF_EARS(68),
    PIRATE_HAT(69),
    PLAGUE_DOCTOR(70),
    MACHETE(71),
    HOCKEY_MASK(72),
    MINER_HELMET(73),
    WINTER_CAP(74),
    ARCHAEOLOGIST_HAT(75),
    ANTENNA(76),
    BALLOON(77),
    BIRD_NEST(78),
    BLACK_BELT(79),
    CAUTION_SIGN(80),
    CHEF_HAT(81),
    COP_HAT(82),
    DO_RAG(83),
    DUM_STICKER(84),
    FEZ(85),
    GENERAL_HAT(86),
    POMPADOUR_HAIR(87),
    HUNTER_HAT(88),
    JUNGLE_HAT(89),
    MINI_CREWMATE(90),
    NINJA_MASK(91),
    RAM_HORNS(92),
    MINI_CREWMATE_SNOWMAN(93),
    GEOFF_KEIGHLEY_MASK(94)
    ;

    final int id;
    private Hat(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Hat fromId(int id) {
        for (Hat color : values()) {
            if (color.getId() == id) {
                return color;
            }
        }

        return null;
    }
}
