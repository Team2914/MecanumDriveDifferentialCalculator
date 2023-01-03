public class Main {
    public static double SIZE = 500.0;
    public static double SCALE = SIZE/20.0;
    public static double UNITLEN = 2/Math.sqrt(8);

    public static double in(double len) {
        return len*SCALE;
    }

    public static double UNITPIXLEN = in(UNITLEN)/SIZE;
    public static void main(String[] args) {
        double[] scalers = findCompensation(0.5,0);
        System.out.println("Front Left Velocity: "+scalers[0]+", "+scalers[1]);
        // scalers[0] => back left wheel scaler
        // scalers[1] => back right wheel scaler

    }

    public static double[] findCompensation(double headingI, double headingJ) {
        double scaleLeft = 1;
        double scaleRight = 1;
        //Draw canvas = new Draw();
        //canvas.setCanvasSize((int) SIZE,(int) SIZE);

        // Draw center line
        /*canvas.setPenRadius(0.005);
        canvas.setPenColor(canvas.BLACK);*/
        double axlePixelDistance = in(Config.axleDistance/2.0)/SIZE;
        double frontAxleY = 0.5+axlePixelDistance;
        double backAxleY = 0.5-axlePixelDistance;
        //canvas.line(0.5,backAxleY,0.5, frontAxleY);

        // Draw front axle line
        /*canvas.setPenRadius(0.01);
        canvas.setPenColor(canvas.BLUE);*/
        double frontAxlePixelLength = in(Config.frontAxleLength/2.0)/SIZE;
        double frontLeftWheelX = 0.5-frontAxlePixelLength;
        double frontRightWheelX = 0.5+frontAxlePixelLength;
        /*canvas.line(frontLeftWheelX, frontAxleY, frontRightWheelX, frontAxleY);
        canvas.setPenColor(canvas.PRINCETON_ORANGE);
        canvas.circle(0.5, frontAxleY, 0.01);*/

        // Draw front left wheel
        /*canvas.setPenRadius(0.01);
        canvas.setPenColor(canvas.GRAY);
        canvas.rectangle(frontLeftWheelX,frontAxleY,in(Config.wheelWidth/2)/SIZE, in(Config.wheelHeight/2)/SIZE);*/

        // Draw front right wheel
        /*canvas.setPenRadius(0.01);
        canvas.setPenColor(canvas.GRAY);
        canvas.rectangle(frontRightWheelX,frontAxleY,in(Config.wheelWidth/2)/SIZE, in(Config.wheelHeight/2)/SIZE);*/

        // Draw back axle line
        /*canvas.setPenRadius(0.01);
        canvas.setPenColor(canvas.BLUE);*/
        double backAxlePixelLength = in(Config.backAxleLength/2.0)/SIZE;
        double backLeftWheelX = 0.5-backAxlePixelLength;
        double backRightWheelX = 0.5+backAxlePixelLength;
        /*canvas.line(backLeftWheelX, backAxleY, backRightWheelX, backAxleY);
        canvas.setPenColor(canvas.PRINCETON_ORANGE);
        canvas.circle(0.5, backAxleY, 0.01);*/

        // Draw back left wheel
        /*canvas.setPenRadius(0.01);
        canvas.setPenColor(canvas.GRAY);
        canvas.rectangle(backLeftWheelX,backAxleY,in(Config.wheelWidth/2)/SIZE, in(Config.wheelHeight/2)/SIZE);*/

        // Draw back right wheel
        /*canvas.setPenRadius(0.01);
        canvas.setPenColor(canvas.GRAY);
        canvas.rectangle(backRightWheelX,backAxleY,in(Config.wheelWidth/2)/SIZE, in(Config.wheelHeight/2)/SIZE);*/

        // Draw center of mass
        double pixelCOMX = in(Config.COMX)/SIZE;
        double pixelCOMY = in(Config.COMY)/SIZE;
        /*canvas.setPenRadius(0.01);
        canvas.setPenColor(canvas.RED);
        canvas.circle(0.5+pixelCOMX, 0.5+pixelCOMY,0.01);*/

        // Build COM to wheel vectors
        Vector frontLeftCOMVector = new Vector(0.5+pixelCOMX,0.5+pixelCOMY, frontLeftWheelX-0.5-pixelCOMX,frontAxleY-0.5-pixelCOMY);
        Vector frontRightCOMVector = new Vector(0.5+pixelCOMX,0.5+pixelCOMY, frontRightWheelX-0.5-pixelCOMX,frontAxleY-0.5-pixelCOMY);
        Vector backLeftCOMVector = new Vector(0.5+pixelCOMX,0.5+pixelCOMY, backLeftWheelX-0.5-pixelCOMX,backAxleY-0.5-pixelCOMY);
        Vector backRightCOMVector = new Vector(0.5+pixelCOMX,0.5+pixelCOMY, backRightWheelX-0.5-pixelCOMX,backAxleY-0.5-pixelCOMY);

        // Draw COM to wheel vectors
        /*frontLeftCOMVector.draw(canvas.RED, 0.005);
        frontRightCOMVector.draw(canvas.RED, 0.005);
        backLeftCOMVector.draw(canvas.RED, 0.005);
        backRightCOMVector.draw(canvas.RED, 0.005);*/

        // Build Heading Vector
        Vector headingVector = new Vector(0.5+pixelCOMX,0.5+pixelCOMY, in(headingI)/SIZE, in(headingJ)/SIZE);
        //headingVector.draw(canvas, canvas.MAGENTA, 0.02);

        // Build wheel drive vectors
        Vector frontLeftDriveVector = new Vector(frontLeftWheelX, frontAxleY, UNITPIXLEN*(-1), UNITPIXLEN);
        Vector frontRightDriveVector = new Vector(frontRightWheelX, frontAxleY, UNITPIXLEN, UNITPIXLEN);
        Vector backLeftDriveVector = new Vector(backLeftWheelX, backAxleY, UNITPIXLEN, UNITPIXLEN);
        Vector backRightDriveVector = new Vector(backRightWheelX, backAxleY, UNITPIXLEN*(-1), UNITPIXLEN);

        // Project heading onto drive vectors
        Vector frontLeftVector = frontLeftDriveVector.parallelProjection(headingVector, frontLeftDriveVector.x, frontLeftDriveVector.y);
        Vector frontRightVector = frontRightDriveVector.parallelProjection(headingVector, frontRightDriveVector.x, frontRightDriveVector.y);
        Vector backLeftVector = backLeftDriveVector.parallelProjection(headingVector, backLeftDriveVector.x, backLeftDriveVector.y);
        Vector backRightVector = backRightDriveVector.parallelProjection(headingVector, backRightDriveVector.x, backRightDriveVector.y);

        backLeftVector.scale(scaleLeft);
        backRightVector.scale(scaleRight);

        // Calc wheel velocity
        double wheelPixelWidth = in(Config.wheelWidth)/SIZE;
        double wheelPixelHeight = in(Config.wheelHeight)/SIZE;
        double frontLeftVel = frontLeftVector.length()*Math.signum(frontLeftVector.dot(frontLeftDriveVector));
        double frontRightVel = frontRightVector.length()*Math.signum(frontRightVector.dot(frontRightDriveVector));
        double backLeftVel = backLeftVector.length()*Math.signum(backLeftVector.dot(backLeftDriveVector));
        double backRightVel = backRightVector.length()*Math.signum(backRightVector.dot(backRightDriveVector));

        // Draw wheel velocity
        /*canvas.setPenRadius(0.01);
        canvas.setPenColor(frontLeftVel>0?canvas.GREEN:canvas.RED);
        canvas.line(frontLeftWheelX-wheelPixelWidth,frontAxleY,frontLeftWheelX-wheelPixelWidth, frontAxleY+frontLeftVel);
        canvas.setPenColor(frontRightVel>0?canvas.GREEN:canvas.RED);
        canvas.line(frontRightWheelX+wheelPixelWidth,frontAxleY,frontRightWheelX+wheelPixelWidth, frontAxleY+frontRightVel);
        canvas.setPenColor(backLeftVel>0?canvas.GREEN:canvas.RED);
        canvas.line(backLeftWheelX-wheelPixelWidth,backAxleY,backLeftWheelX-wheelPixelWidth, backAxleY+backLeftVel);
        canvas.setPenColor(backRightVel>0?canvas.GREEN:canvas.RED);
        canvas.line(backRightWheelX+wheelPixelWidth,backAxleY,backRightWheelX+wheelPixelWidth, backAxleY+backRightVel);*/

        // Calc torque vectors
        Vector frontLeftTorqueVector = frontLeftCOMVector.perpendicularProjection(frontLeftDriveVector,frontLeftDriveVector.x, frontLeftDriveVector.y);
        Vector frontRightTorqueVector = frontRightCOMVector.perpendicularProjection(frontRightDriveVector,frontRightDriveVector.x, frontRightDriveVector.y);
        Vector backLeftTorqueVector = backLeftCOMVector.perpendicularProjection(backLeftDriveVector,backLeftDriveVector.x, backLeftDriveVector.y);
        Vector backRightTorqueVector = backRightCOMVector.perpendicularProjection(backRightDriveVector,backRightDriveVector.x, backRightDriveVector.y);

        // Scale vectors by radius and speed
        frontLeftTorqueVector.scale(frontLeftVel*frontLeftCOMVector.length()*SIZE);
        frontRightTorqueVector.scale(frontRightVel*frontRightCOMVector.length()*SIZE);
        backLeftTorqueVector.scale(backLeftVel*backLeftCOMVector.length()*SIZE);
        backRightTorqueVector.scale(backRightVel*backRightCOMVector.length()*SIZE);

        // Draw torque vectors
        /*frontLeftTorqueVector.draw(canvas, canvas.RED,0.01);
        frontRightTorqueVector.draw(canvas, canvas.RED,0.01);
        backLeftTorqueVector.draw(canvas, canvas.RED,0.01);
        backRightTorqueVector.draw(canvas, canvas.RED,0.01);*/

        // Draw wheel vectors
        /*frontLeftVector.draw(canvas, canvas.GREEN, 0.01);
        frontRightVector.draw(canvas, canvas.GREEN, 0.01);
        backLeftVector.draw(canvas, canvas.GREEN, 0.01);
        backRightVector.draw(canvas, canvas.GREEN, 0.01);*/

        double resScaleRight = backRightTorqueVector.length()==0?1:frontLeftTorqueVector.length()/backRightTorqueVector.length();
        double resScaleLeft = backLeftTorqueVector.length()==0?1:frontRightTorqueVector.length()/backLeftTorqueVector.length();
        double[] result = {frontLeftVel, frontRightVel, backLeftVel*resScaleLeft, backRightVel*resScaleRight};
        return result;
    }
}