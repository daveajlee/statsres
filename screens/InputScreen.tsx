/**
 * Show the input screen with configuration options and a submit button.
 */
import { Appearance, Alert, Image, StyleSheet, Text, TextInput, TouchableOpacity, ScrollView, View } from "react-native";
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

    const [calculateMean, setCalculateMean] = useState<boolean>(false);
    const [calculateMedian, setCalculateMedian] = useState<boolean>(false);
    const [calculateCount, setCalculateCount] = useState<boolean>(false);
    const [calculateMin, setCalculateMin] = useState<boolean>(false);
    const [calculateMax, setCalculateMax] = useState<boolean>(false);
    const [calculateIQR, setCalculateIQR] = useState<boolean>(false);
    const [calculateQ1, setCalculateQ1] = useState<boolean>(false);
    const [calculateQ3, setCalculateQ3] = useState<boolean>(false);
    const [calculateStdDev, setCalculateStdDev] = useState<boolean>(false);

    const [disableMean, setDisableMean] = useState<boolean>(true);
    const [disableMedian, setDisableMedian] = useState<boolean>(true);
    const [disableCount, setDisableCount] = useState<boolean>(true);
    const [disableMin, setDisableMin] = useState<boolean>(true);
    const [disableMax, setDisableMax] = useState<boolean>(true);
    const [disableIQR, setDisableIQR] = useState<boolean>(true);
    const [disableQ1, setDisableQ1] = useState<boolean>(true);
    const [disableQ3, setDisableQ3] = useState<boolean>(true);
    const [disableStdDev, setDisableStdDev] = useState<boolean>(true);

    /**
     * Attempt to perform the calculations based on the configuration supplied by the user.
     */
    function calculateHandler() {
        // Check that at least one number has been entered.
        if ( data.length === 0 || !data.includes(',') || data.split(',').length < 2 ) {
            Alert.alert('Please enter at least two numbers.');
            return;
        }
        // Check that at least one operation is selected.
        if ( !calculateMean && !calculateMedian && !calculateCount && !calculateMin && !calculateMax && !calculateIQR && !calculateQ1 && !calculateQ3 && !calculateStdDev ) {
            Alert.alert('Please select at least one operation to perform.');
            return;
        }
        // Trim any whitespace and remove any comma at the end.
        let formattedText = data.trim().replace(/,\s*$/, "");
        // Initialise variables.
        let [mean, median, count, min, max, iqr, q1, q3, stdDev] = ["", "", "", "", "", "", "", "", ""];
        // Calculate count.
        let dataArray = formattedText.split(',');
        count = "" + dataArray.length;
        // Calculate mean.
        let sum = 0;
        for ( let i = 0; i < dataArray.length; i++ ) {
            sum += parseFloat(dataArray[i]);
        }
        mean = "" + (sum / dataArray.length);
        // Calculate median.
        dataArray.sort((a, b) => parseFloat(a) - parseFloat(b));
        median = "" +calculateMedianFromArray(dataArray);
        // Calculate min.
        min = "" + parseFloat(dataArray[0]);
        // Calculate max.
        max = "" + parseFloat(dataArray[dataArray.length - 1]);
        // Calculate Q1 and Q3.
        if ( dataArray.length % 2 === 0 ) {
            // Calculate Q1
            let lowerHalf = dataArray.slice(0, dataArray.length / 2);
            // If it is even in total, then half will be odd so take half and round up. Subtract 1 because of zero indexing.
            q1 = "" + calculateMedianFromArray(lowerHalf);
            // Calculate Q3
            let upperHalf = dataArray.slice(dataArray.length / 2, dataArray.length);
            // If it is even in total, then half will be odd so take half and round up. Subtract 1 because of zero indexing.
            q3 = "" + calculateMedianFromArray(upperHalf);
        } else {
            // Calculate lower half depending on whether it was even or not.
            let lowerHalf = dataArray.slice(0, Math.floor(dataArray.length / 2));
            if ( lowerHalf.length % 2 === 0 ) {
                q1 = "" + (parseFloat(lowerHalf[lowerHalf.length / 2 - 1]) + parseFloat(lowerHalf[lowerHalf.length / 2])) / 2;
            } else {
                q1 = "" + parseFloat(lowerHalf[Math.floor(lowerHalf.length / 2)]);
            }
            // Calculate upper half depending on whether it was even or not.
            let upperHalf = dataArray.slice(Math.floor(dataArray.length / 2) + 1, dataArray.length);
            if ( upperHalf.length % 2 === 0 ) {
                q3 = "" + (parseFloat(upperHalf[upperHalf.length / 2 - 1]) + parseFloat(upperHalf[upperHalf.length / 2])) / 2;
            } else {
                q3 = "" + parseFloat(upperHalf[Math.floor(upperHalf.length / 2)]);
            }
        }
        // Calculate IQR.
        iqr = "" + (parseFloat(q3) - parseFloat(q1));
        // Calculate standard deviation.
        let varianceSum = 0;
        for ( let i = 0; i < dataArray.length; i++ ) {
            varianceSum += Math.pow((parseFloat(dataArray[i]) - parseFloat(mean)), 2);
        }
        let variance = varianceSum / dataArray.length;
        stdDev = (Math.sqrt(variance)).toFixed(13);
        // Navigate to output screen with results.
        navigation.navigate('OutputScreen', { mean, calculateMean, median, calculateMedian, count, calculateCount, 
            min, calculateMin, max, calculateMax, iqr, calculateIQR, q1, calculateQ1, q3, calculateQ3, stdDev, calculateStdDev });
    }

    /**
     * Helper function to calculate median of an array of numbers.
     */
    function calculateMedianFromArray(numbers: string[]) {
        let median = 0;
        if ( numbers.length % 2 === 0 ) {
            median = (parseFloat(numbers[numbers.length / 2 - 1]) + parseFloat(numbers[numbers.length / 2])) / 2;
        } else {
            median = parseFloat(numbers[Math.floor(numbers.length / 2)]);
        }
        return median;
    }

    /**
     * Set the data that the user entered.
     * @param {string} enteredText the text that the user entered in the data field.
     */
    function dataInputHandler(enteredText: string) {
        setData(enteredText);
        // Validate input to only allow numbers and commas.
        if ( enteredText.includes(',') ) {
            let array = enteredText.split(',');
            console.log('array has length ' + array.length);
            if ( array.length > 1 ) {
                setDisableMean(false);
                setDisableMedian(false);
                setDisableCount(false);
                setDisableMin(false);
                setDisableMax(false);
                setDisableStdDev(false);
                setCalculateMean(true);
                setCalculateMedian(true);
                setCalculateCount(true);
                setCalculateMin(true);
                setCalculateMax(true);
                setCalculateStdDev(true);
            }
            if ( array.length > 3 ) {
                setDisableIQR(false);
                setDisableQ1(false);
                setDisableQ3(false);
                setCalculateIQR(true);
                setCalculateQ1(true);
                setCalculateQ3(true);
            }
        }
    }

    /**
     * Display the screen to the user.
     */
    return (
      <SafeAreaView style={[styles.safeContainer, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
        <ScrollView contentContainerStyle={[styles.container, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
          <Image style={styles.logo} source={logoImage} />
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
                    <OperationSwitch label="Mean" value={calculateMean} onChange={setCalculateMean} disabled={disableMean}/>
                    <OperationSwitch label="Median" value={calculateMedian} onChange={setCalculateMedian} disabled={disableMedian}/>
                    <OperationSwitch label="Count" value={calculateCount} onChange={setCalculateCount} disabled={disableCount}/>
                </View>
                <View style={styles.sameRow}>
                    <OperationSwitch label="Minimum" value={calculateMin} onChange={setCalculateMin} disabled={disableMin}/>
                    <OperationSwitch label="Maximum" value={calculateMax} onChange={setCalculateMax} disabled={disableMax}/>
                </View>
                <View style={styles.sameRow}>
                    <OperationSwitch label="IQR" value={calculateIQR} onChange={setCalculateIQR} disabled={disableIQR}/>
                    <OperationSwitch label="Q1" value={calculateQ1} onChange={setCalculateQ1} disabled={disableQ1}/>
                    <OperationSwitch label="Q3" value={calculateQ3} onChange={setCalculateQ3} disabled={disableQ3}/>
                </View>
                <View style={styles.sameRow}>
                    <OperationSwitch label="Standard Deviation" value={calculateStdDev} onChange={setCalculateStdDev} disabled={disableStdDev}/>
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
        width: 300,
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