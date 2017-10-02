public class Person {
    // Constants
    public static final int maxChildren = 20;
    // Fields
    private String name;
    private Person parent1;
    private Person parent2;
    private Person spouse;
    private int numChildren = 0;
    private Person[] children;
    // Note that this is not optimal, numChildren
    // is not necessarily coupled with the actual number
    // of children

    public Person(String n) {
        name = n;
        numChildren = 0;
        children = new Person[maxChildren];
    }

    public Person getSpouse() {
        return spouse;
    }

    public String getName() {
        return name;
    }

    public Person getParent1() {
        return parent1;
    }

    public Person getParent2() {
        return parent2;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public Person[] getChildren() {
        return children;
    }
    // Setter. When you call setParent(q), q gets marked as a parent
    // of this and this gets added to q's children
    public boolean setParent(Person q) {
        // N: Should really be isSucceeded or isValid
        boolean succeed = false;
        // N: Should really be an exception
        if ( q == null) System.out.println("Parent is null");
        else if (q == this)
            System.out.println("A person cannot be their own parent");
        else if (parent2 != null)
            System.out.println(name + " already has two parents");
        else if (q.numChildren >= maxChildren)
            System.out.println(q.name + " already has " + maxChildren + "children.");
        else {
            q.children[q.numChildren] = this;
            q.numChildren = q.numChildren + 1;
            if (parent1 == null) parent1 = q;
            else parent1 = q;
            succeed = true;
        }
        // N: Little confused in why he does it this way.
        // Just return true if the final else statement is tripped,
        // otherwise return false. Guess his way is more flexible
        return succeed;
    }

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    public boolean marry(Person potentialPartner) {
        // Check if related
        boolean isRelated = potentialPartner.getParent2() == this ||
                potentialPartner.getParent1() == this ||
                this.parent1 == potentialPartner ||
                this.parent2 == potentialPartner;

        if (potentialPartner == null) {
            System.out.print("You can't marry imaginary people, " + name);
            return false;
        }
        if (potentialPartner == this) {
            System.out.print("You can't marry yourself");
            return false;
        }
        if (potentialPartner.getSpouse() != null) {
            System.out.print(potentialPartner.getName() + " is already married");
            return false;
        }
        if (this.spouse != null) {
            System.out.print(name + " is already married");
            return false;
        }
        if (isRelated) {
            System.out.print("Ewww");
            return false;
        }
        potentialPartner.setSpouse(this);
        setSpouse(potentialPartner);
        return true;
    }
    
    public void printSpouse() {
        if (spouse != null) {
            System.out.print(spouse);
        } else {
            System.out.print(name + " is not married");
        }
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public void printChildren() {
        String out = "";
        boolean isEmpty = true;
        for (Person child : children) {
            if (child != null) {
                out = out + child.getName() + " ";
                isEmpty = false;
            }
        }
        if (isEmpty) {
            System.out.print(name + " has no children");
        } else {
            System.out.print(out);
        }
    }
    public void divorce() {
        if (this.spouse != null) {
            this.spouse.setSpouse(null);
            this.spouse = null;
        }
    }
}
