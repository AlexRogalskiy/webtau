/*
 * Copyright 2021 webtau maintainers
 * Copyright 2019 TWO SIGMA OPEN SOURCE, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import React from 'react';
import { Step } from './Step';
import { Registry } from 'react-component-viewer';
import { WebTauStep } from '../../WebTauTest';
import { wrapInLightTheme } from '../../demoUtils';

export function stepsDemo(registry: Registry) {
  add('no children', <Step step={noChildren()} isTopLevel={true} />);
  add('zero elapsed time', <Step step={zeroElapsedTime()} isTopLevel={true} />);
  add('with children', <Step step={withChildren()} isTopLevel={true} />);
  add('rainbow', <Step step={rainbow()} isTopLevel={true} />);
  add('with key value input', <Step step={withKeyValueInput()} isTopLevel={true} />);
  add('with key value output', <Step step={withKeyValueOutput()} isTopLevel={true} />);
  add('with key value input output', <Step step={withKeyValueInputAndOutput()} isTopLevel={true} />);
  add('with key value empty input output', <Step step={withKeyValueEmptyInputAndOutput()} isTopLevel={true} />);

  function add(label: string, element: JSX.Element) {
    registry.add(label + ' [dark]', () => element);
    registry.add(label + ' [light]', wrapInLightTheme(element));
  }
}

function noChildren() {
  return {
    elapsedTime: 200,
    startTime: 0,
    message: [
      {
        type: 'action',
        value: 'executed HTTP GET',
      },
      {
        type: 'url',
        value: 'http://localhost:8080/customers/1',
      },
    ],
  };
}

function zeroElapsedTime() {
  return {
    elapsedTime: 0,
    startTime: 0,
    message: [
      {
        type: 'action',
        value: 'executed HTTP GET',
      },
      {
        type: 'url',
        value: 'http://localhost:8080/customers/1',
      },
    ],
  };
}

function rainbow() {
  return {
    elapsedTime: 200,
    startTime: 0,
    message: [
      {
        type: 'action',
        value: 'executed HTTP GET',
      },
      {
        type: 'warning',
        value: 'something is fishy',
      },
      {
        type: 'url',
        value: 'http://localhost:8080/customers/1',
      },
      {
        type: 'preposition',
        value: ' of',
      },
      {
        type: 'id',
        value: 'identifier',
      },
      {
        type: 'stringValue',
        value: '"hello"',
      },
      {
        type: 'delimiter',
        value: '--',
      },
      {
        type: 'error',
        value: 'ops',
      },
      {
        type: 'selectorType',
        value: 'by css',
      },
      {
        type: 'selectorValue',
        value: '.classn',
      },
    ],
  };
}

function withChildren(): WebTauStep {
  return {
    ...noChildren(),
    startTime: 0,
    children: [
      {
        startTime: 0,
        elapsedTime: 50,
        message: [
          {
            type: 'id',
            value: 'body',
          },
          {
            type: 'matcher',
            value:
              'equals {firstName=FN, lastName=LN}\nmatches:\n\nbody.firstName:   actual: "FN" <java.lang.String>\n                expected: "FN" <java.lang.String>\nbody.lastName:   actual: "LN" <java.lang.String>\n               expected: "LN" <java.lang.String>',
          },
        ],
      },
      {
        elapsedTime: 150,
        startTime: 0,
        message: [
          {
            type: 'id',
            value: 'header.statusCode',
          },
          {
            type: 'matcher',
            value:
              'equals 200\nmatches:\n\nheader.statusCode:   actual: 200 <java.lang.Integer>\n                   expected: 200 <java.lang.Integer>',
          },
        ],
      },
    ],
  };
}

function withKeyValueInputAndOutput() {
  return {
    message: [
      { type: 'action', value: 'set' },
      { type: 'id', value: 'url' },
    ],
    startTime: 1621811973852,
    elapsedTime: 0,
    input: { type: 'WebTauStepInputKeyValue', data: { source: 'manual', url: 'http://localhost:64934', cost: 150 } },
    output: { type: 'WebTauStepOutputKeyValue', data: { port: 3473 } },
  };
}

function withKeyValueEmptyInputAndOutput() {
  return {
    message: [
      { type: 'action', value: 'set' },
      { type: 'id', value: 'url' },
    ],
    startTime: 1621811973852,
    elapsedTime: 0,
    input: { type: 'WebTauStepInputKeyValue', data: {} },
    output: { type: 'WebTauStepOutputKeyValue', data: {} },
  };
}

function withKeyValueInput() {
  return {
    message: [
      { type: 'action', value: 'set' },
      { type: 'id', value: 'url' },
    ],
    startTime: 1621811973852,
    elapsedTime: 0,
    input: { type: 'WebTauStepInputKeyValue', data: { source: 'manual', url: 'http://localhost:64934', cost: 150 } },
  };
}

function withKeyValueOutput() {
  return {
    message: [
      { type: 'action', value: 'set' },
      { type: 'id', value: 'url' },
    ],
    startTime: 1621811973852,
    elapsedTime: 0,
    output: { type: 'WebTauStepOutputKeyValue', data: { port: 3473 } },
  };
}
