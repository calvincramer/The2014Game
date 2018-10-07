/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the2048game;

public class Group {
        public Group(Number n1, Number n2, int direction) {
            this.n1 = n1;
            this.n2 = n2;
            this.direction = direction;
        }
        
        public Number getNum1() {
            return n1;
        }
        
        public Number getNum2() {
            return n2;
        }
        
        public int getDirection() {
            return direction;
        }
        
        
        private Number n1;
        private Number n2;
        private int direction;
    }