import { Appearance } from "react-native";
import { SafeAreaView, ScrollView, StyleSheet, Text } from "react-native";
import { version } from './../package.json';

export default function SettingsScreen() {

    const colorScheme = Appearance.getColorScheme();

    /**
     * Display the screen to the user.
     */
    return (
        <SafeAreaView style={[styles.safeContainer, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
            <ScrollView contentContainerStyle={[styles.container, colorScheme === 'dark' ? styles.darkBackground : styles.lightBackground]}>
                <Text style={[styles.bodyText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>Version: {version}</Text>
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
    bodyText: {
        fontSize: 20,
        fontWeight: 'bold',
        textAlign: 'center',
        paddingBottom: 16,
    },
    darkText: {
        color: 'white',
    },
    lightText: {
        color: 'black',
    },
});