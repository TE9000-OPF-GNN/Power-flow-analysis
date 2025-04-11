// Import PowSyBl classes
import com.powsybl.iidm.network.Network;
import com.powsybl.openreac.OpenReacConfig;
import com.powsybl.openreac.OpenReacParameters;
import com.powsybl.openreac.OpenReacRunner;

public class OptimizeNetwork {
    public static void main(String[] args) {
        // Load network exported from Python
        String inputFile = args[0];
        String outputFile = args[1];
        Network network = Network.read(inputFile);
        
        // Configure OpenReac parameters
        OpenReacParameters parameters = new OpenReacParameters();
        parameters.setObjectiveFunction(OpenReacParameters.ObjectiveFunction.MIN_GENERATION_COST);
        
        // Add voltage constraints (similar to your Python limits)
        parameters.setVoltageRanges(0.95, 1.05);
        
        // Set thermal limits enforcement
        parameters.setEnforceCurrentLimits(true);
        
        // Run the optimizer
        OpenReacConfig config = new OpenReacConfig();
        OpenReacRunner optimizer = new OpenReacRunner(config);
        optimizer.run(network, parameters);
        
        // Save optimized network
        network.write("XIIDM", null, outputFile);
        System.out.println("Optimization completed successfully");
    }
}