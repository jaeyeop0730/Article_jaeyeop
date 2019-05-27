package org.njy.article;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.njy.book.chap11.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class ArticleController {
	
	@Autowired
	ArticleDao articleDao;

	Logger logger = LogManager.getLogger();
	

	@GetMapping("/article/list")
	public void articleList(
			@RequestParam(value = "page", defaultValue = "1") int page,
			Model model) {

		// 페이지당 행의 수와 페이지의 시작점
		final int COUNT = 100;
		int offset = (page - 1) * COUNT;

		List<Article> articleList = articleDao.listArticles(offset, COUNT);
		int totalCount = articleDao.getArticlesCount();
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("articleList", articleList);
	}


	@GetMapping("/article/view")
	public void articleView(@RequestParam("articleId") String articleId,
			Model model) {
		Article article = articleDao.getArticle(articleId);
		model.addAttribute("article", article);
	}


	/*
	@GetMapping("/article/addForm")
	public String articleAddForm(HttpSession session, @SessionAttribute("MEMBER") Member member) {
		
		Object memberObj = session.getAttribute("MEMBER");
		if(memberObj == null)
			return "redirect:/app/loginForm";
		
		return "article/addForm";
	}
	*/


	@PostMapping("/article/add")
	public String articleAdd(Article article, @SessionAttribute("MEMBER") Member member) {
		/*
		Object memberObj = session.getAttribute("MEMBER");
		if(memberObj == null)
			return "redirect:/loginForm";
		
		//article.setUserId("2015041091");
		//article.setName("이현도");
		Member member = (Member) memberObj;
		*/
		article.setUserId(member.getMemberId());
		article.setName(member.getName());
		articleDao.addArticle(article);
		return "redirect:/app/article/list";
	}
	
	/**
	 * 글 수정 화면
	 */
	@GetMapping("/article/updateForm")
	public String articleUpdateForm(@RequestParam("articleId") String articleId,
			Model model, @SessionAttribute("MEMBER") Member member) {
		Article article = articleDao.getArticle(articleId);
		if(article.getUserId().equals(member.getMemberId())) {
			model.addAttribute("article", article);
			return "/article/updateForm";
		} else {
			return "redirect:/app/article/view?articleId=" + articleId + "&mode=FAILURE";
		}
	}
	
	/**
	 * 글 수정
	 */
	@PostMapping("/article/update")
	public String articleUpdate(@RequestParam("articleId") String articleId, @RequestParam("title") String title, @RequestParam("content") String content) {
		articleDao.updateArticle(title, content, articleId);
		return "redirect:/app/article/list";
	}
	
	/**
	 * 글 삭제
	 */
	@GetMapping("article/delete")
	public String articleDelete(@RequestParam("articleId") String articleId, @SessionAttribute("MEMBER") Member member) {
		Article article = articleDao.getArticle(articleId);
		if(article.getUserId().equals(member.getMemberId())) {
			articleDao.deleteArticle(articleId);
			return "redirect:/app/article/list";
		}
		return "redirect:/app/article/view?articleId=" + articleId + "&mode=FAILURE";
	}
}