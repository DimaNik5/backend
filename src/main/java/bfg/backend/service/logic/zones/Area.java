package bfg.backend.service.logic.zones;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Area {
    private String name;
    private Integer widthSecond;
    private Integer longitudeSecond;
    private Integer illumination;
    private Cell[][] cells;

    Area(int illumination, String file, String name){
        this.illumination = illumination;
        this.name = name;

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String[] c = br.readLine().split(" ");
            int w = Integer.parseInt(c[0]);
            int h = Integer.parseInt(c[1]);
            cells = new Cell[h][w];
            for (int i = 0; i < h; i++) {
                String[] con = br.readLine().split(";");
                for (int j = 0; j < w; j++) {
                    cells[i][j] = new Cell(con[j]);
                }
            }
            widthSecond = cells[0][0].getWidthSecond();
            longitudeSecond = cells[0][0].getLongitudeSecond();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWidthSecond() {
        return widthSecond;
    }

    public void setWidthSecond(Integer widthSecond) {
        this.widthSecond = widthSecond;
    }

    public Integer getLongitudeSecond() {
        return longitudeSecond;
    }

    public void setLongitudeSecond(Integer longitudeSecond) {
        this.longitudeSecond = longitudeSecond;
    }

    public Integer getIllumination() {
        return illumination;
    }

    public void setIllumination(Integer illumination) {
        this.illumination = illumination;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void setCells(Cell[][] cells) {
        this.cells = cells;
    }
}
