import { Alert, Appearance, SafeAreaView, ScrollView, StyleSheet, Text, TouchableOpacity, View } from "react-native";

export default function HelpScreen() {

    const colorScheme = Appearance.getColorScheme();

    /**
     * Display the screen to the user.
     */
    return (
        <SafeAreaView style={[styles.safeContainer, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
            <ScrollView contentContainerStyle={[styles.container, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
                <View style={styles.buttonContainer}>
                    <TouchableOpacity style={styles.button} onPress={() => Alert.alert("Coming Soon!", "Not yet available!")}>
                        <Text style={styles.buttonText}>Load Settings</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.button} onPress={() => Alert.alert("Coming Soon!", "Not yet available!")}>
                        <Text style={styles.buttonText}>Getting Started</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.button} onPress={() => Alert.alert("Coming Soon!", "Not yet available!")}>
                        <Text style={styles.buttonText}>Load Output</Text>
                    </TouchableOpacity>
                </View>
                <View style={styles.buttonContainer}>
                    <TouchableOpacity style={styles.button} onPress={() => Alert.alert("Coming Soon!", "Not yet available!")}>
                        <Text style={styles.buttonText}>Output</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.button} onPress={() => Alert.alert("Coming Soon!", "Not yet available!")}>
                        <Text style={styles.buttonText}>Welcome</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.button} onPress={() => Alert.alert("Coming Soon!", "Not yet available!")}>
                        <Text style={styles.buttonText}>Save Output</Text>
                    </TouchableOpacity>
                </View>
                <View style={styles.buttonContainer}>
                    <TouchableOpacity style={styles.button} onPress={() => Alert.alert("Coming Soon!", "Not yet available!")}>
                        <Text style={styles.buttonText}>Input Options</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.button} onPress={() => Alert.alert("Coming Soon!", "Not yet available!")}>
                        <Text style={styles.buttonText}>Save Settings</Text>
                    </TouchableOpacity>
                </View>
            </ScrollView>
        </SafeAreaView>
    );
}

const styles = StyleSheet.create({
    safeContainer: {
        flex: 1,
    },
    darkBackground: {
        backgroundColor: 'black',
    },
    lightBackground: {
        backgroundColor: '#F0F0F0',
    },
    container: {
        flex: 1,
        alignItems: 'center',
        justifyContent: 'center',
    },
    buttonContainer: {
        marginTop: 10,
        flexDirection: 'row',
        width: '100%',
    },
    button: {
        alignItems: 'center',
        backgroundColor: 'rgb(240, 74, 9)',
        width: '30%',
        padding: 10,
        marginBottom: 30,
        marginLeft: 5,
        marginRight: 5,
        borderRadius: 10,
    },
    buttonText: {
        color: 'white',
        fontSize: 20,
        fontWeight: 'bold',
        textAlign: 'center',
    },
});