# new feature
# Tags: optional

Feature: beatport search

  Scenario: Validate search results
    Given open beatport.com
    When search for "solomun" artist
    Then Charts contain "solomun" name