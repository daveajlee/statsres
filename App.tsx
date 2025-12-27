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
import { NavigationContainer } from '@react-navigation/native';

// Define stack navigation
const Stack = createNativeStackNavigator();

function App(): React.JSX.Element {

  return (
    <SafeAreaProvider>
      <NavigationContainer>
        <Stack.Navigator initialRouteName="InputScreen">
          <Stack.Screen name="InputScreen" component={InputScreen} options={{ headerShown: false }} />
          <Stack.Screen name="OutputScreen" component={OutputScreen} options={{ title: 'Output', headerBackVisible: false }} />
        </Stack.Navigator>
      </NavigationContainer>
    </SafeAreaProvider>
  );
}

export default App;
