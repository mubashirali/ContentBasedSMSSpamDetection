# Content Based SMS Spam Detection

Many consumers find unwanted SMS which are annoying and time-consuming and can include commercial messages known as spam. With the popularity of smart phones and cheap SMS packages, such messages are received more frequently than ever before. 
In this project we have propose a solution for SMS spam detection. We have develop a three-level parallel classifier for short text messages and demonstrate the improvement in accuracy over traditional Bayesian email spam filters. 
The combined output of three classifiers; Decision trees, Bayesian and SVM show that these parallel layers of spam filtering classifiers give a much higher level of precision and accuracy.
We give a detail comparison of our classifiers based on experimental results using real training dataset and finally prove how these layers can help users in identifying a spam much accurately then before. 
In order to achieve time constraints we suggest a cloud based implementation of the system.   

# Background

Spam filtering is an automatic document classification to evaluate that he document is spam or not. Automatic document classification makes a collection of similar documents and allocate proper category to them, which is accomplish in two phases.
First phase is feature selection method, which extracts the needed features to classify. While the second phase is decision making process, which choose right category for the result from first phase. It gives the ability to assign right category through machine learning approach. Worth mentioning, if we select every word in learned document as a feature, it loses judgment. To prevent this calculate the weight of information for each word then select featured words for automatic classification.
There has been numerous works on SMS and email spam classification using machine learning techniques. The popular techniques for text classifications are decision trees [3], [4], Naïve Bayes [3]-[5], neural networks [3]-[5], nearest neighbors and later on Support Vector Machine [6]. Though there is lot of techniques and algorithms which have been proposed so far, the text classification is not yet accurate and faultless and still in demand of improvement. In order to improve, we have planned to compare and combine the results of mainly three most used classifiers. This purposed solution would not only result in higher accuracy but also perform a comparison of individual classifiers for a greater satisfaction of user.


Keywords: SMS spam filtering, text classification
