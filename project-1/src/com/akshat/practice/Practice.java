package com.akshat.practice;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Practice {

	public static void main(String[] args) {
		System.out.println("Enter your choice for action on numbers between 1 to 10:");
		System.out.println("1.1 Get squares of all even numbers using streams");
		System.out.println("1.2 Reduce using streams (Sum of all numbers)");
		System.out.println("");
		System.out.println("Enter your choice for string opertaions:");
		System.out.println("2.1 Filter names starting with A using streams");
		System.out.println("2.2 All names to upper case using streams");
		System.out.println("2.3 Filter names starting with A and make then upper case using streams");
		
		try (Scanner scanner = new Scanner(System.in)) {
			String input = scanner.nextLine();
			System.out.println(input);
			List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

			switch (input) {
			case "1.1":
				streamPractice(numbers);
				break;
			case "1.2":
				reduce(numbers);
				break;
			case "2.1":
				stringOperations(input);
				break;
			case "2.2":
				stringOperations(input);
				break;
			case "2.3":
				stringOperations(input);
				break;
			default:
				System.out.println("Invalid Input !!!");
			}
		}

	}

	// Stream filter, map and collect
	private static void streamPractice(List<Integer> numbers) {
		System.out.println(numbers.stream().filter(n -> n % 2 == 0).map(n -> n * 2).collect(Collectors.toList()));
	}

	// Stream Reduce
	private static void reduce(List<Integer> numbers) {
		int sum = numbers.stream().reduce(0, Integer::sum);
		System.out.println("Sum: " + sum);
	}

	private static void stringOperations(String input) {
		List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "Eve");

		List<String> result = null;
		if (input.equalsIgnoreCase("2.1")) {
			result = names.stream().filter(name -> name.startsWith("A")).collect(Collectors.toList());
			System.out.println(result);
			return;
		} else if (input.equalsIgnoreCase("2.2")) {
			result = names.stream().map(String::toUpperCase).collect(Collectors.toList());
			System.out.println(result);
			return;
		} else if (input.equalsIgnoreCase("2.3")) {
			result = names.stream().filter(name -> name.startsWith("A")).map(String::toUpperCase)
					.collect(Collectors.toList());
			System.out.println(result);
			return;
		} else {
			System.out.println("Please enter a valid input and retry");
			return;
		}
	}
}
