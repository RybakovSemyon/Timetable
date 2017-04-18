package ru.rybakovsemyon.timetableproject.model;


import java.util.ArrayList;

public class Buildings {
    private ArrayList<Building> buildings;
    public Buildings(int count){
        buildings = new ArrayList<>(count);
        buildings.add(new Building("Корпус №1(Главный корпус)","1"));
        buildings.add(new Building("Корпус №2","2"));
        buildings.add(new Building("Корпус №3","3"));
        buildings.add(new Building("Корпус №4","4"));
        buildings.add(new Building("Корпус №5","5"));
        buildings.add(new Building("Корпус №6","6"));
        buildings.add(new Building("Корпус №7","7"));
        buildings.add(new Building("Корпус №8","8"));
        buildings.add(new Building("Корпус №9","9"));
        buildings.add(new Building("Корпус №10","61"));
        buildings.add(new Building("Корпус №11","21"));
        buildings.add(new Building("Корпус №12","10"));
        buildings.add(new Building("Корпус №13","62"));
        buildings.add(new Building("Корпус №14","63"));
        buildings.add(new Building("Корпус №15(Центр культуры)","64"));
        buildings.add(new Building("Корпус №16(Научная библиотека)","65"));
        buildings.add(new Building("Корпус №17(Главный корпус СФТИ)","66"));
        buildings.add(new Building("Корпус №18(Общежитие №4)","67"));
        buildings.add(new Building("Корпус №19(Общежитие №5)","68"));
        buildings.add(new Building("Корпус №20(Общежитие №1)","69"));
        buildings.add(new Building("Корпус №21(Ботанический сад)","70"));
        buildings.add(new Building("Корпус №22(ИОА СО РАН)","71"));
        buildings.add(new Building("Корпус №23(ИСЭ СО РАН)","72"));
        buildings.add(new Building("Корпус №24(ИХН СО РАН)","73"));
        buildings.add(new Building("Корпус №25(ИФПМ СО РАН)","74"));
        buildings.add(new Building("Корпус №26(Клиники СГМУ)","75"));
        buildings.add(new Building("Корпус №27(ТОКПБ)","76"));
        buildings.add(new Building("Корпус №28(ТГТРК)","77"));
        buildings.add(new Building("Корпус №29(Студийный корпус)","78"));
        buildings.add(new Building("Корпус №30(Департамент природных ресурсов ТО)","79"));
        buildings.add(new Building("Корпус №31(Главный корпус ТУСУР)","81"));
        buildings.add(new Building("Корпус №32(Психотерапевтический центр)","101"));
        buildings.add(new Building("Корпус №33(Томскстат)","121"));
        buildings.add(new Building("Корпус №34(Студия новомедийных технологий)","141"));
        buildings.add(new Building("Корпус №35(ОГАУ 'КЦСОН ТО')","161"));
        buildings.add(new Building("Корпус №36(ТНИИКиФ)","200"));
        buildings.add(new Building("Корпус №37(Арт-мастерская)","220"));
        buildings.add(new Building("Корпус №38(Индорсофт)","240"));
        buildings.add(new Building("Корпус №39(Институт МКЭС)","260"));
        buildings.add(new Building("Корпус №40(Общежитие №8)","280"));
        buildings.add(new Building("Корпус №41(Стадион ТГУ)","281"));
    }

    public ArrayList<Building> getBuildings(){
        return buildings;
    }

}
