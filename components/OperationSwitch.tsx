import { Appearance, Switch, StyleSheet, Text, View } from "react-native";
import { useEffect, useState } from "react";

type OperationSwitchProps = {
    label: string;
    value: boolean;
    onChange: Function;
    disabled: boolean;
}

export default function OperationSwitch({label, value, onChange, disabled}: OperationSwitchProps) {

    const colorScheme = Appearance.getColorScheme();
    const [isEnabled, setIsEnabled] = useState<boolean>(value);
    const [isDisabled, setIsDisabled] = useState<boolean>(disabled);

    useEffect(() => {
        setIsEnabled(value);
        setIsDisabled(disabled);
    }, [value, disabled]);

    function toggleSwitch() {
        setIsEnabled(!isEnabled);
        onChange(!isEnabled);
    }

    return (
        <View style={styles.switchContainer}>
            <Text style={[styles.bodyText, colorScheme === 'dark' ? styles.darkText : styles.lightText]}>{label}:</Text>
            <Switch
                trackColor={{false: '#767577', true: '#81b0ff'}}
                thumbColor={isEnabled ? '#f5dd4b' : '#f4f3f4'}
                ios_backgroundColor="#3e3e3e"
                onValueChange={toggleSwitch}
                value={isEnabled}
                disabled={isDisabled}
            />
        </View>
    );
}

const styles = StyleSheet.create({
    switchContainer: {
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
        paddingBottom: 16
    },
    darkText: {
        color: 'white'
    },
    lightText: {
        color: 'black'
    },
});