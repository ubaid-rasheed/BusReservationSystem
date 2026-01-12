public class Bus {

    int id, cap;
    String name, departure, dest, time;
    double fare;

    boolean[] seats;
    String[] pNames;
    int[] pAges;
    char[] pGenders;
    int availSeats;

    Bus(int i, String n, String s, String d, String t, int c, double f) {
        id = i;
        name = n;
        departure = s;
        dest = d;
        time = t;
        cap = c;
        fare = f;

        seats = new boolean[c];
        pNames = new String[c];
        pAges = new int[c];
        pGenders = new char[c];
        availSeats = c;
    }

    boolean isAvail(int s) {
        return s >= 0 && s < cap && !seats[s];
    }

    void book(int s, String n, int a, char g) {
        seats[s] = true;
        pNames[s] = n;
        pAges[s] = a;
        pGenders[s] = g;
        availSeats--;
    }

    boolean cancel(int s) {
        if (s < 0 || s >= cap || !seats[s]) return false;
        seats[s] = false;
        pNames[s] = null;
        pAges[s] = 0;
        pGenders[s] = ' ';
        availSeats++;
        return true;
    }
}
