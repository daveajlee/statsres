import { Appearance, Image, ScrollView, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { useNavigation } from "@react-navigation/native";
import { SafeAreaView } from "react-native-safe-area-context";
import OperationResult from "../components/OperationResult";

type NavigationStackParams = {
  navigate: Function;
  setOptions: Function;
}

type OutputScreenProps = {
    route: any;
}

export default function OutputScreen({route}: OutputScreenProps) {

    const colorScheme = Appearance.getColorScheme();

    const logoImage = require('./../assets/images/logo-1024.png');

    const navigation = useNavigation<NavigationStackParams>();

    console.log('OutputScreen: Mean is ' + route.params.mean);

    /**
     * Attempt to activate the user with the code supplied.
     */
    function newHandler() {
        navigation.navigate('InputScreen');
    }

    /**
     * Display the screen to the user.
     */
    return (
      <SafeAreaView style={styles.safeContainer}>
        <ScrollView contentContainerStyle={[styles.container, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
            <Image style={styles.logo} source={logoImage}/>
            <View style={styles.headerContainer}>
                <Text style={[styles.headerText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Output</Text>
            </View>
            { route.params.calculateMean && <OperationResult label="Mean" value={route.params.mean} /> }
            { route.params.calculateMedian && <OperationResult label="Median" value={route.params.median} /> }
            { route.params.calculateCount && <OperationResult label="Count" value={route.params.count} /> }
            { route.params.calculateMin && <OperationResult label="Minimum" value={route.params.min} /> }
            { route.params.calculateMax && <OperationResult label="Maximum" value={route.params.max} /> }
            { route.params.calculateIQR && <OperationResult label="IQR" value={route.params.iqr} /> }
            { route.params.calculateQ1 && <OperationResult label="Q1" value={route.params.q1} /> }
            { route.params.calculateQ3 && <OperationResult label="Q3" value={route.params.q3} /> }
            { route.params.calculateStdDev && <OperationResult label="Standard Deviation" value={route.params.stdDev} /> }
            <View style={styles.buttonContainer}>
                <TouchableOpacity style={styles.button} onPress={newHandler}>
                    <Text style={styles.buttonText}>New Analysis</Text>
                </TouchableOpacity>
            </View>
          </ScrollView>
      </SafeAreaView>
    )

}

const styles = StyleSheet.create({
    safeContainer: {
        flex: 1,
    },
    logo: {
        marginTop: 10, 
        width: 128, 
        height: 128
    },
    darkBackground: {
        backgroundColor: 'black',
    },
    lightBackground: {
        backgroundColor: '#F0F0F0',
    },
    darkText: {
        color: 'white'
    },
    lightText: {
        color: 'black'
    },
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    headerContainer: {
        paddingTop: 30
    },
    bodyContainer: {
        paddingTop: 20,
        width: '100%',
        alignItems: 'center',
        justifyContent: 'center'
    },
    usernameContainer: {
        flexDirection: 'column',
        width: '80%',
    },
    passwordContainer: {
        flexDirection: 'column',
        width: '80%',
        marginTop: 10
    },
    activationContainer: {
        flexDirection: 'column',
        width: '80%',
        marginTop: 10
    },
    headerText: {
        fontSize: 32,
        fontWeight: 'bold',
        textAlign: 'center'
    },
    bodyText: {
        fontSize: 20,
        fontWeight: 'bold',
        textAlign: 'center',
        paddingBottom: 16
    },
    textInputLight: {
        borderWidth: 1,
        borderColor: '#e4d0ff',
        backgroundColor: 'white',
        color: '#120438',
        borderRadius: 6,
        width: '100%',
        padding: 8
    },
    textInputDark: {
        borderWidth: 1,
        borderColor: 'white',
        backgroundColor: 'black',
        color: 'white',
        borderRadius: 6,
        width: '100%',
        padding: 8
    },
    buttonContainer: {
        marginTop: 20,
        flexDirection: 'row'
    },
    button: {
        alignItems: "center",
        backgroundColor: "#e72f41ff",
        width: '90%',
        padding: 10,
        marginBottom: 30,
        borderRadius: 50
    },
    buttonText: {
        color: 'white',
        fontSize: 20,
        fontWeight: 'bold',
        textAlign: 'center',
    },
});
