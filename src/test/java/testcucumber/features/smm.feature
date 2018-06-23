Feature: techhouse

  Scenario: Find tech house query results
    Given User navigates to google search
    When User is looking for "tech house"
    Then The page results contain "Beatport"