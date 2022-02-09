import pdb
import random
from venv import create
import utils
import time
import string

# make dictionary of letters that give a list of lists
# for instance "e" -> [ [e is the first letter], [e is the second letter], [e is the third letter], [e is the fourth letter], [e is the fifth letter] ]
letters2Words = {
    "A": [[], [], [], [], []],
    "B": [[], [], [], [], []],
    "C": [[], [], [], [], []],
    "D": [[], [], [], [], []],
    "E": [[], [], [], [], []],
    "F": [[], [], [], [], []],
    "G": [[], [], [], [], []],
    "H": [[], [], [], [], []],
    "I": [[], [], [], [], []],
    "J": [[], [], [], [], []],
    "K": [[], [], [], [], []],
    "L": [[], [], [], [], []],
    "M": [[], [], [], [], []],
    "N": [[], [], [], [], []],
    "O": [[], [], [], [], []],
    "P": [[], [], [], [], []],
    "Q": [[], [], [], [], []],
    "R": [[], [], [], [], []],
    "S": [[], [], [], [], []],
    "T": [[], [], [], [], []],
    "U": [[], [], [], [], []],
    "V": [[], [], [], [], []],
    "W": [[], [], [], [], []],
    "X": [[], [], [], [], []],
    "Y": [[], [], [], [], []],
    "Z": [[], [], [], [], []]
}
ourWordList = utils.readwords("allwords5.txt")
# loop through wordlist adding them to letters to word
for i in ourWordList:
    for l in range(len(i)):
        letters2Words[i[l]][l].append(i)


# remove the words that do contain the given letter
def removeWordsWithLetter(guessList, letter):
    tmp = []
    for i in guessList:
        if letter in i:
            # add word to list to remove after
            tmp.append(i)
    # convert the 2 to sets and do a set subtraction
    r = set(guessList) - set(tmp)
    return list(r)


# remove the words that do not contain the given letter
def removeWordsWithoutLetter(guessList, letter):
    tmp = []
    for i in guessList:
        if letter not in i:
            # add word to list to remove after
            tmp.append(i)
    # convert the 2 to sets and do a set subtraction
    return list(set(guessList) - set(tmp))

# initialize guess list based on correct letters


def createGuessList(correctWord):
    # GET possible words for each correct letter
    tmp = []
    if(correctWord[0] != ""):
        # just add to the list
        tmp = letters2Words[correctWord[0]][0]
    if(correctWord[1] != ""):
        if(tmp == []):
            tmp = letters2Words[correctWord[1]][1]
        else:
            # intersect the lists
            tmp = list(set(tmp).intersection(letters2Words[correctWord[1]][1]))
    if(correctWord[2] != ""):
        if tmp == []:
            tmp = letters2Words[correctWord[2]][2]
        else:
            # intersect the lists
            tmp = list(set(tmp).intersection(letters2Words[correctWord[2]][2]))
    if(correctWord[3] != ""):
        if tmp == []:
            tmp = letters2Words[correctWord[3]][3]
        else:
            # intersect the lists
            tmp = list(set(tmp).intersection(letters2Words[correctWord[3]][3]))
    if(correctWord[4] != ""):
        if tmp == []:
            tmp = letters2Words[correctWord[4]][4]
        else:
            # intersect the lists
            tmp = list(set(tmp).intersection(letters2Words[correctWord[4]][4]))
    return tmp


# set the positions list and dictionary
def setPositions(feedback, guesses, correctWord, positions, lettersInWord, lettersNotInWord):
    for i in range(len(feedback)):
        guess = guesses[i]
        feed = feedback[i]

        for i in range(len(guess)):
            # p o l l s
            if feed[i] == 0:
                # positions[guess[i]][i] = 0
                allGrey = True
                for j in range(len(guess)):
                    if(j != i and guess[j] == guess[i]):
                        # if another instance of the letter that was grey is in the word, but it isn't grey
                        if(feed[j] != 0):
                            allGrey = False
                            break

                if(allGrey):
                    lettersNotInWord.append(guess[i])
                    positions[guess[i]][0] = 0
                    positions[guess[i]][1] = 0
                    positions[guess[i]][2] = 0
                    positions[guess[i]][3] = 0
                    positions[guess[i]][4] = 0
                else:
                    # other wise we know that letter isn't in JUST this position
                    positions[guess[i]][i] = 0
            elif feed[i] == 1:
                # right letter wrong position
                lettersInWord[guess[i]] = 1
                positions[guess[i]][i] = 0
            else:
                # correct position
                lettersInWord[guess[i]] = 1
                positions[guess[i]][i] = 2
                correctWord[i] = guess[i]


# method to remove any words that have letters that are in the word but wrong position
def removeWrongPositionWords(guessList, positions, lettersInWord, lettersNotInWord):
    # loop through guess list
    tmp = []
    for i in range(len(guessList)):
        word = guessList[i]
        # loop through word and see if it has a letter in word, but in wrong position
        for l in range(len(word)):
            if(positions[word[l]][l] == 0):
				#get rid of guesses that have letter that we know doesn't go at that position
                tmp.append(guessList[i])
                continue

    return list(set(guessList) - set(tmp))


def moveRepeatedLetterWordsBack(words):

    wordsToNumRepeats = {}
    # loop through words
    for i in range(len(words)):
        word = words[i]
        # find number of unique letters
        uniqueLets = set(word)
        # map the string to its number of unique letters
        wordsToNumRepeats[word] = len(uniqueLets)

    # sort dictionary by value
    s = sorted(wordsToNumRepeats.items(), key=lambda kv: kv[1])

    # convert keys to list
    sortedDict = dict(s)
    words = list(sortedDict.keys())

    # reverse list so now words with most unique letter are in front
    words.reverse()

    return words


# method to help us find the last letter when all others are known and our number of guesses
# is <= 4
def findLastLetter(guessList, index, lettersInWord, correctWord, guesses):
    letters = []
    wordList = utils.readwords("allwords5.txt")

    # add to letters list the possible letters from that unknown position in guess list
    for i in range(len(guessList)):
        letters.append(guessList[i][index])

    # remove letters that we already know are in the word.
    inWord = list(lettersInWord.keys())
    for i in range(len(lettersInWord)):
        if(inWord[i] in letters):
            letters.remove(inWord[i])

    # to remove duplicates
    letters = list(set(letters))
    # print("possible letters are ", letters)

    # loop through words list to find which words have the most letters
    # in our list of possible letters created above
    max = 0
    tmp = []
    for i in range(len(wordList)):
        current = 0
        word = wordList[i]

        # count the number of unknown letters in each word
        for j in range(len(word)):
            if(word[j] in letters):
                current += 1

        # if its a new max add it to a list
        if(current >= max):
            if(current > max):
                tmp = []
                tmp.append(word)
                max = current
            else:
                max = current
                tmp.append(word)

    # move possible guesses with most unique characters to front of list
    tmp = moveRepeatedLetterWordsBack(tmp)
    # print(tmp)

    # we are now returning a guess that has the most letters that could possibly be the last letter
    # sort the words
    for i in range(len(tmp)):
        if(tmp[i] not in guesses):
            return tmp[i]

    return tmp[0]


def findLast2Letters(guessList, correctWord):
    '''Method to find all the possible letters that could fill the last 2 unknown letters'''

    pos1 = 0
    pos2 = 1

    # first I need to get the 2 positions
    for i in range(len(correctWord)):
        if correctWord[i] == "":
            # have first position that is empty
            pos1 = i
            break
    for i in range(len(correctWord)-1, -1, -1):
        if correctWord[i] == "":
            pos2 = i
            break

    # now that we have positions we need to get all the possible letter that can go in either of those positions
    letters = []
    for word in guessList:
        letters.append(word[pos1])
        letters.append(word[pos2])

    # turn the letter list into a set to remove all duplicates
    letters = list(set(letters))

    # get total wordList
    wordList = utils.readwords("allwords5.txt")

    max = 0
    tmp = []
    for i in range(len(wordList)):
        current = 0
        word = wordList[i]

        # count the number of unknown letters in each word
        for j in range(len(word)):
            if(word[j] in letters):
                current += 1

        # if its a new max add it to a list
        if(current >= max):
            if(current > max):
                tmp = []
                tmp.append(word)
                max = current
            else:
                max = current
                tmp.append(word)

    # for i in range(0,len(tmp)):
    # 	word = tmp[i]
    # 	#set the index of that word to be an empty string
    # 	for j in range(len(word)):
    # 		if(word.count(word[j]) > 1):
    # 			tmp[i] = ""
    # 			break

    tmp = moveRepeatedLetterWordsBack(tmp)
    # sort the words
    # tmp = sortWords(tmp) #bizarre thing when both sorts are implemented on findlastletter methods
    # the success percentage drops by 1 and the average tries goes up by .1-.2

    return tmp[0]


def sortWords(guessList):
    '''to sort words by putting words with more frequent letters towards the front and words with double letters towards the rear'''
    # for each word in the guessList gets it numerical value of how good of a guess it is
    words = {word: wordValue(word) for word in guessList}

    # now we have a dictionary of words mapping to each words own point value
    # turn back into a list and then sort it based on those dictionary point values
    # t = sorted(words.items(), key=lambda x: x[1], reverse=True)
    sortedWords = [key for (key, value) in sorted(
        words.items(), key=lambda x:x[1], reverse=True)]

    # return the new list
    return sortedWords


def wordValue(word):
    points = 0
    # make word a set to reduce chances of guessing
    # because that is one less letter to add points to the word
    tmp = set(word)

    # give points for each letter in set
    # based on numbers from doing a frequency analysis of all letters in the wordlist
    # we get the total occurence of each letter divide it by 26 and then round to 2 decimal places
    pointValues = {
        "A": 230.38,
        "B": 62.57,
        "C": 78.00,
        "D": 94.35,
        "E": 256.23,
        "F": 48.47,
        "G": 63.23,
        "H": 67.69,
        "I": 144.58,
        "J": 11.19,
        "K": 57.88,
        "L": 129.65,
        "M": 76.00,
        "N": 113.54,
        "O": 170.69,
        "P": 77.65,
        "Q": 4.31,
        "R": 159.92,
        "S": 256.35,
        "T": 126.73,
        "U": 96.58,
        "V": 26.69,
        "W": 39.96,
        "X": 11.08,
        "Y": 79.77,
        "Z": 16.69
    }
    for letter in tmp:
        points += pointValues[letter]

    # loop through word giving bonus points for specific letters in specific places
    for i in range(len(word)):
        if i == 0:
            # first letter
            # points added could change based on experimentation
            if i == "S" or i == "s":
                points += 50
            if i == "C" or i == "C":
                points += 15
            if i == "B" or i == "b":
                points += 15
            if i == "T" or i == "t":
                points += 15
            if i == "P" or i == "p":
                points += 15
            if i == "A" or i == "a":
                points += 15
            if i == "F" or i == "f":
                points += 15
        elif i == 1:
            # second letter
            if i == "A" or i == "a":
                points += 15
            if i == "O" or i == "o":
                points += 15
            if i == "R" or i == "r":
                points += 15
            if i == "E" or i == "e":
                points += 15
            if i == "I" or i == "i":
                points += 15
            if i == "L" or i == "l":
                points += 15
            if i == "U" or i == "u":
                points += 15
            if i == "H" or i == "h":
                points += 15
        elif i == 2:
            # third letter
            if i == "A" or i == "a":
                points += 15
            if i == "I" or i == "i":
                points += 15
            if i == "O" or i == "o":
                points += 15
            if i == "E" or i == "e":
                points += 15
            if i == "U" or i == "u":
                points += 15
            if i == "R" or i == "r":
                points += 15
            if i == "N" or i == "n":
                points += 15
        elif i == 3:
            # fourth letter
            if i == "E" or i == "e":
                points += 50
            if i == "N" or i == "n":
                points += 15
            if i == "S" or i == "s":
                points += 15
            if i == "A" or i == "a":
                points += 15
            if i == "L" or i == "l":
                points += 15
            if i == "I" or i == "i":
                points += 15
            if i == "R" or i == "r":
                points += 15
            if i == "C" or i == "c":
                points += 15
            if i == "T" or i == "t":
                points += 15
            if i == "O" or i == "o":
                points += 15
        else:
            # fifth letter
            if i == "E" or i == "e":
                points += 15
            if i == "Y" or i == "y":
                points += 15
            if i == "T" or i == "t":
                points += 15
            if i == "R" or i == "r":
                points += 15
            if i == "L" or i == "l":
                points += 15
            if i == "H" or i == "h":
                points += 15
            if i == "N" or i == "n":
                points += 15
            if i == "D" or i == "d":
                points += 15

    # reduce points for a word that ends in S, its just not very likely
    if word[-1] == "S" or word[-1] == "s":
        points -= 100  # subject to change based on experimentation

    # return points
    return points


def makeguess(wordlist, guesses=[], feedback=[]):
    """Guess a word from the available wordlist, (optionally) using feedback 
    from previous guesses.

    Parameters
    ----------
    wordlist : list of str
                                    A list of the valid word choices. The output must come from this list.
    guesses : list of str
                                    A list of the previously guessed words, in the order they were made, 
                                    e.g. guesses[0] = first guess, guesses[1] = second guess. The length 
                                    of the list equals the number of guesses made so far. An empty list 
                                    (default) implies no guesses have been made.
    feedback : list of lists of int
                                    A list comprising one list per word guess and one integer per letter 
                                    in that word, to indicate if the letter is correct (2), almost 
                                    correct (1), or incorrect (0). An empty list (default) implies no 
                                    guesses have been made.

    Output
    ------
    word : str
                                    The word chosen by the AI for the next guess.
    """

    correctWord = ["", "", "", "", ""]
    lettersNotInWord = []
    lettersInWord = {}
    positions = {
        "A": [1, 1, 1, 1, 1],
        "B": [1, 1, 1, 1, 1],
        "C": [1, 1, 1, 1, 1],
        "D": [1, 1, 1, 1, 1],
        "E": [1, 1, 1, 1, 1],
        "F": [1, 1, 1, 1, 1],
        "G": [1, 1, 1, 1, 1],
        "H": [1, 1, 1, 1, 1],
        "I": [1, 1, 1, 1, 1],
        "J": [1, 1, 1, 1, 1],
        "K": [1, 1, 1, 1, 1],
        "L": [1, 1, 1, 1, 1],
        "M": [1, 1, 1, 1, 1],
        "N": [1, 1, 1, 1, 1],
        "O": [1, 1, 1, 1, 1],
        "P": [1, 1, 1, 1, 1],
        "Q": [1, 1, 1, 1, 1],
        "R": [1, 1, 1, 1, 1],
        "S": [1, 1, 1, 1, 1],
        "T": [1, 1, 1, 1, 1],
        "U": [1, 1, 1, 1, 1],
        "V": [1, 1, 1, 1, 1],
        "W": [1, 1, 1, 1, 1],
        "X": [1, 1, 1, 1, 1],
        "Y": [1, 1, 1, 1, 1],
        "Z": [1, 1, 1, 1, 1]
    }

    # python wordle.py -ai best_ai.py --fast -n 500
    # python wordle.py -ai best_ai.py --superfast --playall

    if(len(guesses) == 0):
        # return "TRACE"
        return "ADIEU"
        # here you can see all the previous ways we were starting our first guess
        # return "RAISE"
        return random.choice(["RAISE", "RAILE", "ARISE", "ARIEL"])
        # return random.choice(["ROATE", "REAIS", "SLATE", "AEGIS", "LARES", "RALES", "TARES", "NARES", "ARLES", "SIREN", "RAISE", "QUERY", "RENTS", "SNARE", "EARNS", "STOAE", "SANER", "CANOE", "TEARS", "STEAM", "ADIEU", "SOARE", "AROSE", "IRATE"])

    if(len(guesses) == 1):
        # with trace or raise, use SHOUT NOULS
        return "SNORT"

    # # set the possible positions and lettersNotInWord
    setPositions(feedback, guesses, correctWord, positions,
                 lettersInWord, lettersNotInWord)

    # # this could be 								3
    if(len(guesses) == 2 and len(lettersInWord) < 3):
        return "PALMY"
    # if(len(guesses) == 2):
    #     return "PALMY"

    # get an initial guess list using letters we know are correct
    guessList = createGuessList(correctWord)
    # print("guess list up here" , guessList)

    if(len(guessList) == 0):
        # have no correct letters, so just add all words and the filters will remove the letters we know don't work
        guessList = wordlist

    # if a letter is in both the correct words and lettersNotInWord remove the letter from lettersNotInWord
    for i in correctWord:
        if(i in lettersNotInWord):
            lettersNotInWord.remove(i)

    # repeat above except for the lettersInWord Set
    for i in lettersInWord:
        if(i in lettersNotInWord):
            lettersNotInWord.remove(i)

    # FILTER GUESSLIST as much as possible to REDUCE possible words to return

    for j in lettersNotInWord:
        guessList = removeWordsWithLetter(guessList, j)

    # remove words that do not contain letters that are in the word(specifically the letters that we know are in the word but not what position)
    for j in lettersInWord:
        if(j != ""):
            guessList = removeWordsWithoutLetter(guessList, j)


    # remove words where letters don't correspond with positions dictionary
    # THIS WAS CAUSING CRASH
    guessList = removeWrongPositionWords(
        guessList, positions, lettersInWord, lettersNotInWord)



    # if we are only missing one letter and have plenty of options and at least 2 guesses left
    # find a word that uses all or all-1 of the possible letters and use that so the next guess has much more information
    if(len(guesses) <= 4):
        # if there is only 1 letter we don't know
        if(correctWord.count("") == 1 and len(guessList) > 2):
            index = 0
            for i in range(len(correctWord)):
                if(correctWord[i] == ""):
                    index = i
                    break

            g = findLastLetter(
                guessList, index, lettersInWord, correctWord, guesses)

            print("guess should be", g)
            if (g != ""):
                return g

    # if we are down to 2 uknown letters and we have many reamining guess
    if (len(guesses) <= 3):
        if(correctWord.count("") == 2 and len(guessList) > 2):
			#call this method to make a guess that will try to give us the last two letters
            g = findLast2Letters(guessList, correctWord)

            if(g != ""):
                return g

	# if(guesses[len(guesses)] )
	
    # print("Returning random choice from guessList")
    if(len(guessList) == 0):
        return input("guessList Empty Select Value to pass: ")

    # reorder the list based on ranking of best words to choose
    # place double letters and words with q, x, v, z at the end
    guessList = sortWords(guessList)
    return guessList[0]

if __name__ == "__main__":
    wordlist = utils.readwords("allwords5.txt")
    print(f"AI: 'My next choice would be {makeguess(wordlist)}'")
