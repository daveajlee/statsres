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

import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer, DefaultTheme, DarkTheme } from '@react-navigation/native';

import { Appearance } from 'react-native';

// Define stack navigation
const Stack = createNativeStackNavigator();

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

function App(): React.JSX.Element {

  const colorScheme = Appearance.getColorScheme();

  return (
    <SafeAreaProvider>
      <NavigationContainer theme={colorScheme === 'dark' ? MyDarkTheme : MyDefaultTheme}>
        <Stack.Navigator initialRouteName="InputScreen">
          <Stack.Screen name="InputScreen" component={InputScreen} options={{ headerShown: false }} />
          <Stack.Screen name="OutputScreen" component={OutputScreen} options={{ headerShown: false }} />
        </Stack.Navigator>
      </NavigationContainer>
    </SafeAreaProvider>
  );
}

export default App;
