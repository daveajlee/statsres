import { Appearance, StyleSheet, Text, View } from "react-native";

type OperationResultProps = {
    label: string;
    value: number;
}

export default function OperationResult({label, value}: OperationResultProps) {

    const colorScheme = Appearance.getColorScheme();

    return (
        <View style={styles.resultContainer}>
            <Text style={[styles.bodyText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{label}:</Text>
            <Text style={[styles.bodyText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{value}</Text>
        </View>
    );
}

const styles = StyleSheet.create({
    resultContainer: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginTop: 10,
        paddingRight: 20,
    },
    bodyText: {
        fontSize: 20,
        fontWeight: 'bold',
        textAlign: 'center',
        paddingBottom: 16,
        paddingRight: 10
    },
    darkText: {
        color: 'white'
    },
    lightText: {
        color: 'black'
    },
});