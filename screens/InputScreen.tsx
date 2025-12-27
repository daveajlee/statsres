/**
 * Show the input screen with configuration options and a submit button.
 */
import { Appearance, Image, StyleSheet, Text, TextInput, TouchableOpacity, ScrollView, View } from "react-native";
import { useNavigation } from "@react-navigation/native";
import { useState } from "react";
import { SafeAreaView } from "react-native-safe-area-context";
import OperationSwitch from "../components/OperationSwitch";

type NavigationStackParams = {
  navigate: Function;
  setOptions: Function;
}

export default function InputScreen() {

    const colorScheme = Appearance.getColorScheme();

    const logoImage = require('./../assets/images/logo-1024.png');
    
    const navigation = useNavigation<NavigationStackParams>();

    const [data, setData] = useState<string>('');

    const [calculateMean, setCalculateMean] = useState<boolean>(true);
    const [calculateMedian, setCalculateMedian] = useState<boolean>(true);
    const [calculateCount, setCalculateCount] = useState<boolean>(true);
    const [calculateMin, setCalculateMin] = useState<boolean>(true);
    const [calculateMax, setCalculateMax] = useState<boolean>(true);
    const [calculateIQR, setCalculateIQR] = useState<boolean>(true);
    const [calculateQ1, setCalculateQ1] = useState<boolean>(true);
    const [calculateQ3, setCalculateQ3] = useState<boolean>(true);
    const [calculateStdDev, setCalculateStdDev] = useState<boolean>(true);

    /**
     * Attempt to perform the calculations based on the configuration supplied by the user.
     */
    function calculateHandler() {
        // Initialise variables.
        let [mean, median, count, min, max, iqr, q1, q3, stdDev] = [0, 0, 0, 0, 0, 0, 0, 0, ""];
        // Calculate count.
        let dataArray = data.split(',');
        count = dataArray.length;
        // Calculate mean.
        let sum = 0;
        for ( let i = 0; i < dataArray.length; i++ ) {
            sum += parseFloat(dataArray[i]);
        }
        mean = sum / dataArray.length;
        // Calculate median.
        dataArray.sort((a, b) => parseFloat(a) - parseFloat(b));
        if ( dataArray.length % 2 === 0 ) {
            median = (parseFloat(dataArray[dataArray.length / 2 - 1]) + parseFloat(dataArray[dataArray.length / 2])) / 2;
        } else {
            median = parseFloat(dataArray[Math.floor(dataArray.length / 2)]);
        }
        // Calculate min.
        min = parseFloat(dataArray[0]);
        // Calculate max.
        max = parseFloat(dataArray[dataArray.length - 1]);
        // Calculate Q1 and Q3.
        if ( dataArray.length % 2 === 0 ) {
            let lowerHalf = dataArray.slice(0, dataArray.length / 2);
            q1 = (parseFloat(lowerHalf[lowerHalf.length / 2 - 1]) + parseFloat(lowerHalf[lowerHalf.length / 2])) / 2;
        } else {
            // Calculate lower half depending on whether it was even or not.
            let lowerHalf = dataArray.slice(0, Math.floor(dataArray.length / 2));
            if ( lowerHalf.length % 2 === 0 ) {
                q1 = (parseFloat(lowerHalf[lowerHalf.length / 2 - 1]) + parseFloat(lowerHalf[lowerHalf.length / 2])) / 2;
            } else {
                q1 = parseFloat(lowerHalf[Math.floor(lowerHalf.length / 2)]);
            }
            // Calculate upper half depending on whether it was even or not.
            let upperHalf = dataArray.slice(Math.floor(dataArray.length / 2) + 1, dataArray.length);
            if ( upperHalf.length % 2 === 0 ) {
                q3 = (parseFloat(upperHalf[upperHalf.length / 2 - 1]) + parseFloat(upperHalf[upperHalf.length / 2])) / 2;
            } else {
                q3 = parseFloat(upperHalf[Math.floor(upperHalf.length / 2)]);
            }
        }
        // Calculate IQR.
        iqr = q3 - q1;
        // Calculate standard deviation.
        let varianceSum = 0;
        for ( let i = 0; i < dataArray.length; i++ ) {
            varianceSum += Math.pow((parseFloat(dataArray[i]) - mean), 2);
        }
        let variance = varianceSum / dataArray.length;
        stdDev = (Math.sqrt(variance)).toFixed(13);
        // Navigate to output screen with results.
        navigation.navigate('OutputScreen', { mean, median, count, min, max, iqr, q1, q3, stdDev });
    }

    /**
     * Set the data that the user entered.
     * @param {string} enteredText the text that the user entered in the data field.
     */
    function dataInputHandler(enteredText: string) {
        setData(enteredText);
    }

    /**
     * Display the screen to the user.
     */
    return (
      <SafeAreaView style={styles.safeContainer}>
        <ScrollView contentContainerStyle={[styles.container, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
          <Image
            style={styles.logo}
            source={logoImage}
          />
          <View style={styles.headerContainer}>
            <Text style={[styles.headerText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Perform Analysis</Text>
          </View>
          <ScrollView contentContainerStyle={styles.bodyContainer}>
            <View style={styles.dataContainer}>
                <Text style={[styles.bodyText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Data:</Text>
                <TextInput style={colorScheme === 'dark' ? styles.textInputDark : styles.textInputLight} placeholder='Comma separated numbers' onChangeText={dataInputHandler} value={data} multiline={true}/>
            </View>
            <View style={styles.operationsContainer}>
                <Text style={[styles.bodyText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Operation(s):</Text>
                <View style={styles.sameRow}>
                    <OperationSwitch label="Mean" value={calculateMean} onChange={setCalculateMean}/>
                    <OperationSwitch label="Median" value={calculateMedian} onChange={setCalculateMedian}/>
                    <OperationSwitch label="Count" value={calculateCount} onChange={setCalculateCount}/>
                </View>
                <View style={styles.sameRow}>
                    <OperationSwitch label="Minimum" value={calculateMin} onChange={setCalculateMin}/>
                    <OperationSwitch label="Maximum" value={calculateMax} onChange={setCalculateMax}/>
                </View>
                <View style={styles.sameRow}>
                    <OperationSwitch label="IQR" value={calculateIQR} onChange={setCalculateIQR}/>
                    <OperationSwitch label="Q1" value={calculateQ1} onChange={setCalculateQ1}/>
                    <OperationSwitch label="Q3" value={calculateQ3} onChange={setCalculateQ3}/>
                </View>
                <View style={styles.sameRow}>
                    <OperationSwitch label="Standard Deviation" value={calculateStdDev} onChange={setCalculateStdDev}/>
                </View>
            </View>
            <View style={styles.buttonContainer}>
                <TouchableOpacity style={styles.button} onPress={calculateHandler}>
                    <Text style={styles.buttonText}>Calculate</Text>
                </TouchableOpacity>
            </View>
          </ScrollView>
        </ScrollView>
      </SafeAreaView>
    )
}

const styles = StyleSheet.create({
    safeContainer: {
        flex: 1,
    },
    sameRow: {
        flexDirection: 'row',
        marginLeft: 10,
        marginRight: 10,
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
    dataContainer: {
        flexDirection: 'column',
        width: '80%',
    },
    operationsContainer: {
        flexDirection: 'column',
        width: '80%',
        marginTop: 20
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
        width: 300,
        height: 100,
        padding: 8,
        textAlign: 'center'
    },
    textInputDark: {
        borderWidth: 1,
        borderColor: 'white',
        backgroundColor: 'black',
        color: 'white',
        borderRadius: 6,
        width: '100%',
        height: 100,
        padding: 8,
        textAlign: 'center'
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