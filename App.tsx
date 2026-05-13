/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React from 'react';
import { SafeAreaProvider } from 'react-native-safe-area-context';
import InputScreen from './screens/InputScreen';
import OutputScreen from './screens/OutputScreen';
import SettingsScreen from './screens/SettingsScreen';
import HelpScreen from './screens/HelpScreen';

import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer, DefaultTheme, DarkTheme } from '@react-navigation/native';
import { useNavigation } from '@react-navigation/native';
import { Alert, Appearance, View } from 'react-native';
import IconButton from './components/IconButton';

// Define stack navigation
const Stack = createNativeStackNavigator();

type NavigationStackParams = {
  navigate: Function;
}

const MyDefaultTheme = {
  ...DefaultTheme,
  colors: {
    ...DefaultTheme.colors,
    background: '#F0F0F0',
    primary: 'black',
  },
};

const MyDarkTheme = {
  ...DarkTheme,
  colors: {
    ...DarkTheme.colors,
    background: 'black',
    primary: 'white',
  },
};

function App() {

  const colorScheme = Appearance.getColorScheme();

  function RootStack() {

  // Navigation hook
  const navigation = useNavigation<NavigationStackParams>();

    return (
      <Stack.Navigator initialRouteName="InputScreen" screenOptions={{ headerStyle: {
            backgroundColor: 'rgb(240, 74, 9)'}, headerTitleStyle: {
            fontWeight: 'bold', color: 'white'
          }, headerTitleAlign: 'left', headerTintColor: 'white'}}>
          <Stack.Screen name="InputScreen" component={InputScreen} options={{ title: 'Perform Analysis', 
            headerRight: () => <View style={{flexDirection: 'row'}}>
                {/*<View>
                    <IconButton onPress={() => Alert.alert("Coming Soon!", "Not yet available!")} iconName='folder-open-outline' color="white" />
                </View>
                <View style={{marginLeft: 10}}>
                    <IconButton onPress={() => Alert.alert("Coming Soon!", "Not yet available!")} iconName='save-outline' color="white" />
                </View>
                <View style={{marginLeft: 10}}>
                    <IconButton onPress={() => navigation.navigate('HelpScreen')} iconName='help-circle-outline' color="white" />
                </View>*/}
                <View style={{marginLeft: 10}}>
                    <IconButton onPress={() => navigation.navigate('SettingsScreen')} iconName='settings-outline' color="white" />
                </View>
              </View>
           }} />
          <Stack.Screen name="OutputScreen" component={OutputScreen} options={{ title: 'Output',
            headerRight: () => <View style={{flexDirection: 'row'}}>
                {/*<View style={{marginLeft: 10}}>
                    <IconButton onPress={() => navigation.navigate('HelpScreen')} iconName='help-circle-outline' color="white" />
                </View>*/}
                <View style={{marginLeft: 10}}>
                    <IconButton onPress={() => navigation.navigate('SettingsScreen')} iconName='settings-outline' color="white" />
                </View>
              </View>
           }} />
           <Stack.Screen name="HelpScreen" component={HelpScreen} options={{ title: 'Statsres Help' }} />
          <Stack.Screen name="SettingsScreen" component={SettingsScreen} options={{ title: 'Settings' }} />
      </Stack.Navigator>
    );
  }

  return (
    <SafeAreaProvider>
      <NavigationContainer theme={colorScheme === 'dark' ? MyDarkTheme : MyDefaultTheme}>
        <RootStack />
      </NavigationContainer>
    </SafeAreaProvider>
  );
}

export default App;
