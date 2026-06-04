# State Diagram
1. Payment Transaction states
    ```mermaid
    stateDiagram-v2
        state successful <<choice>>
        [*] --> Created
        Created --> Processing
        Processing --> successful
        successful --> Success
        successful --> Failed
        Failed --> Reversal
        Success --> [*]
        Reversal --> [*]
    ```
