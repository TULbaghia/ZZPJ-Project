INSERT INTO public.article VALUES (1, '10.1007/978-3-662-61039-8_01', 'NOT REQUIRED', 'TITLE 1');
INSERT INTO public.article VALUES (2, '10.1007/978-3-662-61039-8_02', 'NOT REQUIRED', 'TITLE 2');
INSERT INTO public.article VALUES (3, '10.1007/978-3-662-61039-8_03', 'NOT REQUIRED', 'TITLE 3');
INSERT INTO public.article VALUES (4, '10.1007/978-3-662-61039-8_04', 'NOT REQUIRED', 'TITLE 4');
INSERT INTO public.article VALUES (5, '10.1007/978-3-662-61039-8_05', 'NOT REQUIRED', 'TITLE 5');
INSERT INTO public.article VALUES (6, '10.1007/978-3-662-61039-8_06', 'NOT REQUIRED', 'TITLE 6');
INSERT INTO public.article VALUES (7, '10.1007/978-3-662-61039-8_07', 'NOT REQUIRED', 'TITLE 7');
INSERT INTO public.article VALUES (8, '10.1007/978-3-662-61039-8_08', 'NOT REQUIRED', 'TITLE 8');
INSERT INTO public.article VALUES (9, '10.1007/978-3-662-61039-8_09', 'NOT REQUIRED', 'TITLE 9');



INSERT INTO public.topic VALUES (1, 'Topic 1');
INSERT INTO public.topic VALUES (2, 'Topic 2');
INSERT INTO public.topic VALUES (3, 'Topic 3');
INSERT INTO public.topic VALUES (4, 'Topic 4');
INSERT INTO public.topic VALUES (5, 'Topic 5');
INSERT INTO public.topic VALUES (6, 'Topic 6');

--                              (article_id, topic_id)

INSERT INTO public.topic_article VALUES (1, 1);
INSERT INTO public.topic_article VALUES (2, 1);

INSERT INTO public.topic_article VALUES (2, 2);

INSERT INTO public.topic_article VALUES (8, 2);
INSERT INTO public.topic_article VALUES (9, 2);

INSERT INTO public.topic_article VALUES (8, 6);


-- OTHER


INSERT INTO public.word VALUES (1, false, '', 'apple');
INSERT INTO public.word VALUES (2, false, '', 'banana');
INSERT INTO public.word VALUES (3, false, '', 'grapes');
INSERT INTO public.word VALUES (4, false, '', 'watermelon');
INSERT INTO public.word VALUES (5, false, '', 'guava');
INSERT INTO public.word VALUES (6, false, '', 'cherries');
INSERT INTO public.word VALUES (7, false, '', 'oranges');
INSERT INTO public.word VALUES (8, false, '', 'strawberries');
INSERT INTO public.word VALUES (9, false, '', 'pomegranate');
INSERT INTO public.word VALUES (10, false, '', 'pears');
INSERT INTO public.word VALUES (11, false, '', 'peaches');
INSERT INTO public.word VALUES (12, false, '', 'starfruit');

--                                           (article_id, word_id

INSERT INTO public.article_word VALUES (1, 1, 1, 1);
INSERT INTO public.article_word VALUES (2, 1, 2, 2);
INSERT INTO public.article_word VALUES (3, 1, 3, 3);
INSERT INTO public.article_word VALUES (4, 1, 4, 4);
INSERT INTO public.article_word VALUES (5, 1, 5, 5);
INSERT INTO public.article_word VALUES (6, 1, 6, 6);
INSERT INTO public.article_word VALUES (7, 1, 7, 7);
INSERT INTO public.article_word VALUES (8, 1, 8, 8);
INSERT INTO public.article_word VALUES (9, 1, 9, 9);

INSERT INTO public.article_word VALUES (10, 1, 1, 2);
INSERT INTO public.article_word VALUES (11, 1, 1, 3);
